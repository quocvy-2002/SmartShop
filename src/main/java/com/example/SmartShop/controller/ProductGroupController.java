package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateProductGroup;
import com.example.SmartShop.dto.request.UpdateProductGroup;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.ProductGroupResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.service.ProductGroupService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/product-groups")
public class ProductGroupController {
    ProductGroupService productGroupService;

    @PostMapping
    ApiResponse<ProductGroupResponse> createProductGroup(@Valid @RequestBody CreateProductGroup request) {
        log.info("Received request to create product group: {}", request.getProductGroupName());
        return ApiResponse.<ProductGroupResponse>builder()
                .result(productGroupService.createProductGroup(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProductGroupResponse>> getProductGroups(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching product groups with page: {}, size: {}", page, size);
        return ApiResponse.<List<ProductGroupResponse>>builder()
                .result(productGroupService.getProductGroups(page, size))
                .build();
    }

    @PutMapping("/{productGroupId}")
    ApiResponse<ProductGroupResponse> updateProductGroup(
            @PathVariable Integer productGroupId,
            @Valid @RequestBody UpdateProductGroup request) {
        log.info("Received request to update product group ID: {}", productGroupId);
        return ApiResponse.<ProductGroupResponse>builder()
                .result(productGroupService.updateProductGroup(productGroupId, request))
                .build();
    }

    @DeleteMapping("/{productGroupId}")
    ApiResponse<String> deleteProductGroup(@PathVariable Integer productGroupId) {
        log.info("Received request to delete product group ID: {}", productGroupId);
        productGroupService.deleteProductGroup(productGroupId);
        return ApiResponse.<String>builder()
                .result("Product Group has been deleted")
                .build();
    }

    @GetMapping("/products/{productGroupId}")
    ApiResponse<List<Product>> getProductsByGroupId(
            @PathVariable("productGroupId") Integer productGroupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching products for product group ID: {}", productGroupId);
        return ApiResponse.<List<Product>>builder()
                .result(productGroupService.getProductsByGroupId(productGroupId, page, size))
                .build();
    }

    @GetMapping("/{productGroupId}")
    ApiResponse<ProductGroupResponse> getProductGroup(@PathVariable("productGroupId") Integer productGroupId) {
        log.info("Fetching product group with ID: {}", productGroupId);
        return ApiResponse.<ProductGroupResponse>builder()
                .result(productGroupService.getProductGroup(productGroupId))
                .build();
    }
}