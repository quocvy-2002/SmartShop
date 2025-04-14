package com.example.SmartShop.service;

import com.example.SmartShop.dto.request.CreateProductRequest;
import com.example.SmartShop.dto.request.UpdateProductRequest;
import com.example.SmartShop.dto.response.ProductResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.ProductGroup;
import com.example.SmartShop.entity.Store;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.ProductMapper;
import com.example.SmartShop.repository.ProductGroupRepository;
import com.example.SmartShop.repository.ProductRepository;
import com.example.SmartShop.repository.StoreRepository;
import com.example.SmartShop.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    ProductGroupRepository productGroupRepository;
    StoreRepository storeRepository;
    UserRepository userRepository;

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request){
        Store store = storeRepository.findByStoreId(request.getStoreId());
        if (store == null) {
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);
        }
        ProductGroup productGroup = productGroupRepository.findByProductGroupId(request.getProductGroupId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_GROUP_NOT_EXISTED));
        if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Giá sản phẩm phải lớn hơn 0");
        }
        if (request.getStock() < 0) {
            throw new RuntimeException("Số lượng tồn kho không thể âm");
        }
        Product product = productMapper.toProduct(request);
        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public Page<Product> searchProducts( String productName,
                                         BigDecimal minPrice,
                                         BigDecimal maxPrice,
                                         String sortBy,
                                         String sortDirection,
                                         int page,
                                         int size){
        Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByFilters(productName, minPrice, maxPrice, pageable);
    }

    public ProductResponse getProduct(Integer productId){
        return productMapper.toProductResponse(productRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
    }

    public List<ProductResponse> getMyProduct() {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Store store = user.getStore();
        if (store == null) {
            throw new AppException(ErrorCode.STORE_NOT_EXISTED);
        }
        List<Product> products = storeRepository.findAllByNameStore(store.getNameStore());
        return productMapper.toProductResponse(products);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    public ProductResponse updateProduct(Integer productId, UpdateProductRequest request){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        if (request.getProductGroupId() != null) {
            ProductGroup productGroup = productGroupRepository.findById(request.getProductGroupId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_GROUP_NOT_EXISTED));
            product.setProductGroup(productGroup);
        }
        LocalDateTime now = LocalDateTime.now();
        product.setUpdatedAt(now);
        productMapper.updateProduct(product, request);
        return productMapper.toProductResponse(productRepository.save(product));
    }

}
