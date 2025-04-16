package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateSearchHistory;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.SearchHistoryResponse;
import com.example.SmartShop.entity.SeachHistory;
import com.example.SmartShop.service.SearchHistoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RestController
@RequestMapping("/search/history")
public class SearchHistoryController {
    SearchHistoryService searchHistoryService ;

    @PostMapping
    ApiResponse<SearchHistoryResponse> createQuery(@Valid @RequestBody CreateSearchHistory request){
        return ApiResponse.<SearchHistoryResponse>builder()
                .result(searchHistoryService.creatQuery(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<SeachHistory>> getSearchHistory(){
        return ApiResponse.<List<SeachHistory>>builder()
                .result(searchHistoryService.getSearchHistory())
                .build();
    }
    @DeleteMapping("/{searchId}")
    ApiResponse<String> deleteSearchHistory(@PathVariable("searchId") Integer searchId){
        searchHistoryService.deleteSearchHistory(searchId);
        return ApiResponse.<String>builder().result("Query been deleted").build();
    }
    @DeleteMapping
    ApiResponse<String> clearAllSearchHistory(){
        searchHistoryService.clearAllSearchHistory();
        return ApiResponse.<String>builder().result("History been deleted").build();
    }
}
