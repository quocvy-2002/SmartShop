package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateProductGroup;
import com.example.SmartShop.dto.request.UpdateProductGroup;
import com.example.SmartShop.dto.response.ProductGroupResponse;
import com.example.SmartShop.entity.ProductGroup;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductGroupMapper {
    ProductGroup toProductGroup(CreateProductGroup request);
    ProductGroupResponse toProductGroupResponse(ProductGroup productGroup);
    void updateProductGroup(@MappingTarget ProductGroup productGroup, UpdateProductGroup request);
}
