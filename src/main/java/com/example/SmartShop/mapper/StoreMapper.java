package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateStoreRequest;
import com.example.SmartShop.dto.request.UpdateStoreRequest;
import com.example.SmartShop.dto.response.StoreResponse;
import com.example.SmartShop.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StoreMapper {
    Store toStore(CreateStoreRequest request);
    StoreResponse toStoreResponse(Store store);
    void updateStore(@MappingTarget Store store, UpdateStoreRequest request);
}
