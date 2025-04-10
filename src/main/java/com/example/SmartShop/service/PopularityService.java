package com.example.SmartShop.service;

import com.example.SmartShop.dto.response.PopularProductResponse;
import com.example.SmartShop.entity.*;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.PopularProductMapper;
import com.example.SmartShop.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PopularityService {
    ProductRepository productRepository;
    PopularProductRepository popularProductRepository;
    OrderDetailRepository orderDetailRepository;
    ReviewRepository reviewRepository;
    WishListRepository wishListRepository;
    SearchHistoryRepository searchHistoryRepository;
    CartItemRepository cartItemRepository;
    ProductViewRepository productViewRepository;
    PopularProductMapper popularProductMapper;

    // Trọng số cho từng yếu tố
    private static final double W1 = 0.30; // salesScore
    private static final double W2 = 0.20; // reviewScore
    private static final double W3 = 0.15; // wishListScore
    private static final double W4 = 0.15; // searchScore
    private static final double W5 = 0.10; // cartScore
    private static final double W6 = 0.05; // viewScore
    private static final double W7 = 0.05; // timeDecayFactor
    private static final double LAMBDA = 0.05; // Hệ số giảm thời gian
    private static final int TIME_WINDOW_DAYS = 7; // Khoảng thời gian tính điểm (30 ngày)
    private static final double FEATURED_PERCENTAGE = 0.1; // tỉ lệ phần trăm sản phẩm được cho là nổi bật

    public void calculatePopularityScores() {
        // Lấy tất cả sản phẩm
        List<Product> products = productRepository.findAll();

        // Tính giá trị tối đa để chuẩn hóa
        long maxQuantitySold = getMaxQuantitySold(products);
        long maxNumberOfReviews = getMaxNumberOfReviews(products);
        long maxNumberOfWishLists = getMaxNumberOfWishLists(products);
        long maxNumberOfSearches = getMaxNumberOfSearches(products);
        long maxNumberOfCartItems = getMaxNumberOfCartItems(products);
        long maxNumberOfViews = getMaxNumberOfViews(products);

        // Tính điểm cho từng sản phẩm
        for (Product product : products) {
            PopularProduct popularProduct = popularProductRepository.findByProduct(product)
                    .orElse(PopularProduct.builder()
                            .product(product)
                            .productGroup(product.getProductGroup())
                            .build());

            double popularityScore = calculatePopularityScore(
                    product,
                    maxQuantitySold,
                    maxNumberOfReviews,
                    maxNumberOfWishLists,
                    maxNumberOfSearches,
                    maxNumberOfCartItems,
                    maxNumberOfViews
            );

            popularProduct.setPopularityScore(BigDecimal.valueOf(popularityScore));
            popularProduct.setCalculatedAt(LocalDate.now());
            popularProductRepository.save(popularProduct);
        }
    }

    private double calculatePopularityScore(
            Product product,
            long maxQuantitySold,
            long maxNumberOfReviews,
            long maxNumberOfWishLists,
            long maxNumberOfSearches,
            long maxNumberOfCartItems,
            long maxNumberOfViews) {
        LocalDate startDate = LocalDate.now().minusDays(TIME_WINDOW_DAYS);
        LocalDateTime startDateTime = startDate.atStartOfDay();

        // 1. Tính salesScore
        Long totalQuantitySold = orderDetailRepository.sumQuantityByProductIdAndDate(product.getProductId(), startDate);
        double salesScore = (totalQuantitySold != null && maxQuantitySold > 0) ? (double) totalQuantitySold / maxQuantitySold : 0;

        // 2. Tính reviewScore
        Long numberOfReviews = reviewRepository.countByProductId(product.getProductId());
        Double avgRating = reviewRepository.avgRatingByProductId(product.getProductId());
        double reviewScore = (numberOfReviews != null && avgRating != null && maxNumberOfReviews > 0)
                ? ((double) numberOfReviews / maxNumberOfReviews) * (avgRating / 5.0) : 0;

        // 3. Tính wishListScore
        Long numberOfWishLists = wishListRepository.countByProductId(product.getProductId());
        double wishListScore = (numberOfWishLists != null && maxNumberOfWishLists > 0) ? (double) numberOfWishLists / maxNumberOfWishLists : 0;

        // 4. Tính searchScore
        Long numberOfSearches = searchHistoryRepository.countByProductNameAndDate(product.getProductName(), startDate);
        double searchScore = (numberOfSearches != null && maxNumberOfSearches > 0) ? (double) numberOfSearches / maxNumberOfSearches : 0;

        // 5. Tính cartScore
        Long numberOfCartItems = cartItemRepository.countByProductId(product.getProductId());
        double cartScore = (numberOfCartItems != null && maxNumberOfCartItems > 0) ? (double) numberOfCartItems / maxNumberOfCartItems : 0;

        // 6. Tính viewScore
        Long numberOfViews = productViewRepository.countByProductIdAndDate(product.getProductId(), startDateTime);
        double viewScore = (numberOfViews != null && maxNumberOfViews > 0) ? (double) numberOfViews / maxNumberOfViews : 0;

        // 7. Tính timeDecayFactor
        LocalDate lastActivityDate = getLastActivityDate(product);
        long daysSinceLastActivity = ChronoUnit.DAYS.between(lastActivityDate, LocalDate.now());
        double timeDecayFactor = Math.exp(-LAMBDA * daysSinceLastActivity);

        // Tính điểm tổng
        double popularityScore = (W1 * salesScore) + (W2 * reviewScore) + (W3 * wishListScore) +
                (W4 * searchScore) + (W5 * cartScore) + (W6 * viewScore) + (W7 * timeDecayFactor);

        // Chuẩn hóa về thang 0-100
        return popularityScore * 100;
    }

    // Các phương thức hỗ trợ để lấy giá trị tối đa
    private long getMaxQuantitySold(List<Product> products) {
        LocalDate startDate = LocalDate.now().minusDays(TIME_WINDOW_DAYS);
        return products.stream()
                .map(p -> orderDetailRepository.sumQuantityByProductIdAndDate(p.getProductId(), startDate))
                .filter(quantity -> quantity != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L);
    }

    private long getMaxNumberOfReviews(List<Product> products) {
        return products.stream()
                .map(p -> reviewRepository.countByProductId(p.getProductId()))
                .filter(count -> count != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L);
    }

    private long getMaxNumberOfWishLists(List<Product> products) {
        return products.stream()
                .map(p -> wishListRepository.countByProductId(p.getProductId()))
                .filter(count -> count != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L);
    }

    private long getMaxNumberOfSearches(List<Product> products) {
        LocalDate startDate = LocalDate.now().minusDays(TIME_WINDOW_DAYS);
        return products.stream()
                .map(p -> searchHistoryRepository.countByProductNameAndDate(p.getProductName(), startDate))
                .filter(count -> count != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L);
    }

    private long getMaxNumberOfCartItems(List<Product> products) {
        return products.stream()
                .map(p -> cartItemRepository.countByProductId(p.getProductId()))
                .filter(count -> count != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L);
    }

    private long getMaxNumberOfViews(List<Product> products) {
        LocalDateTime startDate = LocalDate.now().minusDays(TIME_WINDOW_DAYS).atStartOfDay();
        return products.stream()
                .map(p -> productViewRepository.countByProductIdAndDate(p.getProductId(), startDate))
                .filter(count -> count != null)
                .mapToLong(Long::longValue)
                .max()
                .orElse(1L);
    }

    // Lấy ngày hoạt động gần nhất
    private LocalDate getLastActivityDate(Product product) {
        LocalDate defaultDate = LocalDate.now().minusDays(TIME_WINDOW_DAYS);

        // Từ OrderDetail
        LocalDate fromOrder = orderDetailRepository.findTopByProductOrderByOrderCreatedAtDesc(product)
                .map(od -> od.getOrder().getCreatedAt().toLocalDate())
                .orElse(defaultDate);

        // Từ Review
        LocalDate fromReview = reviewRepository.findTopByProductOrderByCreatedAtDesc(product)
                .map(Review::getCreatedAt)
                .orElse(defaultDate);

        // Từ WishList
        LocalDate fromWishList = wishListRepository.findTopByProductOrderByCreatedAtDesc(product)
                .map(w -> LocalDate.parse(w.getCreated_at()))
                .orElse(defaultDate);

        // Từ SearchHistory
        LocalDate fromSearch = searchHistoryRepository.findTopByProductNameOrderByCreatedAtDesc(product.getProductName())
                .map(SeachHistory::getCreatedAt)
                .orElse(defaultDate);

        // Từ CartItem
        LocalDate fromCart = cartItemRepository.findTopByProductOrderByCartUpdatedAtDesc(product)
                .map(ci -> ci.getCart().getUpdatedAt())
                .orElse(defaultDate);

        // Từ ProductView
        LocalDate fromView = productViewRepository.findTopByProductOrderByViewedAtDesc(product)
                .map(pv -> pv.getViewedAt().toLocalDate())
                .orElse(defaultDate);

        // Lấy ngày gần nhất
        return List.of(fromOrder, fromReview, fromWishList, fromSearch, fromCart, fromView)
                .stream()
                .max(LocalDate::compareTo)
                .orElse(defaultDate);
    }
    public List<PopularProductResponse> getFeaturedProducts() {
        LocalDate latestDate = popularProductRepository.findLatestCalculatedAt();
        if (latestDate == null) {
            throw new AppException(ErrorCode.CALCULATED_AT_NOT_EXISTED_IN_POPULARPRODUCT);
        }
        List<PopularProduct> latestProducts = popularProductRepository.findByCalculatedAtOrderByPopularityScoreDesc(latestDate);
        if(latestProducts.isEmpty()) {
            throw  new AppException(ErrorCode.NO_PRODUCT_WITH_CALCULATED_AT);
        }
        int totalProducts = latestProducts.size();
        int topPercentIndex = (int) (totalProducts * FEATURED_PERCENTAGE);
        if (topPercentIndex == 0) {
            log.warn("Số lượng sản phẩm quá ít để chọn top {}%.", FEATURED_PERCENTAGE * 100);
        }
        double threshold = latestProducts.get(topPercentIndex - 1).getPopularityScore().doubleValue();
        log.info("Ngưỡng popularity_score cho top 10% sản phẩm nổi bật: {}", threshold);
        return latestProducts.stream()
                .filter(popularProduct -> popularProduct.getPopularityScore().doubleValue() >= threshold)
                .map(popularProductMapper::toPopularProductResponse)
                .collect(Collectors.toList());
    }
}