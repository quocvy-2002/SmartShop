package com.example.SmartShop.mapper;


import com.example.SmartShop.dto.request.CreateProductRequest;
import com.example.SmartShop.dto.request.UpdateProductRequest;
import com.example.SmartShop.dto.response.ProductResponse;
import com.example.SmartShop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(CreateProductRequest product);
    ProductResponse toProductResponse(Product product);
    List<ProductResponse> toProductResponse(List<Product> products);

    void updateProduct(@MappingTarget Product product, UpdateProductRequest request);
}
