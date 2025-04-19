package com.example.SmartShop.service;

import com.example.SmartShop.dto.request.CreateWishListRequest;
import com.example.SmartShop.dto.response.WishListResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.User;
import com.example.SmartShop.entity.WishList;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.WishListMapper;
import com.example.SmartShop.repository.ProductRepository;
import com.example.SmartShop.repository.UserRepository;
import com.example.SmartShop.repository.WishListRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class WishListService {
    WishListRepository wishListRepository;
    WishListMapper wishListMapper;
    ProductRepository productRepository;
    UserRepository UserRepository;


    public List<WishListResponse> getWishListByUserName() {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        List<WishList> listWishList = wishListRepository.findByUser_UserName(userName);
        return listWishList.stream().map(wishListMapper::toWishListResponse).collect(Collectors.toList());
    }

    public WishListResponse createWishList(CreateWishListRequest request) {
        var context = SecurityContextHolder.getContext();
        String userName = context.getAuthentication().getName();
        if(wishListRepository.existsByUser_UserNameAndProduct_ProductId(userName, request.getProductId())) {
            throw new AppException(ErrorCode.WISHLIST_EXISTED);
        }
        Optional<User> userObj = UserRepository.findByUserName(userName);
        User user = userObj.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Optional<Product> productObj =productRepository.findByProductId(request.getProductId());
        Product product = productObj.orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        WishList wishList = WishList.builder()
                .user(user)
                .product(product)
                .created_at(LocalDateTime.now())
                .build();
        return wishListMapper.toWishListResponse(wishListRepository.save(wishList));
    }
    public String removeFromWishList(Integer wishListId) {
        WishList wishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new AppException(ErrorCode.WISHLIST_NOT_EXISTED));
        wishListRepository.delete(wishList);
        return "WishList removed";
    }
}
