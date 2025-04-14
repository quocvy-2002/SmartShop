package com.example.SmartShop.mapper;

import com.example.SmartShop.dto.request.CreateSearchHistory;
import com.example.SmartShop.dto.response.SearchHistoryResponse;
import com.example.SmartShop.entity.SeachHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SearchHistoryMapper {
    SeachHistory toSeachHistory(CreateSearchHistory request);
    SearchHistoryResponse toSearchHistoryResponse(SeachHistory searchHistory);
}
