package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateWishListRequest;
import com.example.SmartShop.dto.response.WishListResponse;
import com.example.SmartShop.entity.WishList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishListMapper {
    WishList toWishList(CreateWishListRequest request);
    WishListResponse toWishListResponse(WishList wishList);
}
