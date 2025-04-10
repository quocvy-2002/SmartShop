package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.response.PopularProductResponse;
import com.example.SmartShop.entity.PopularProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PopularProductMapper {

    PopularProductResponse toPopularProductResponse(PopularProduct popularProduct);
}
