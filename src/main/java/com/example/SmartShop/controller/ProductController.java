package com.example.SmartShop.controller;

import com.example.SmartShop.dto.request.CreateProductRequest;
import com.example.SmartShop.dto.request.UpdateProductRequest;
import com.example.SmartShop.dto.response.ApiResponse;
import com.example.SmartShop.dto.response.ProductResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@RestController
@RequestMapping("/product")
public class ProductController {
    ProductService productService;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }
    @DeleteMapping("/{productId}")
    ApiResponse<String> deleteUser(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }
    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateUser(@PathVariable Integer productId, @Valid @RequestBody UpdateProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, request))
                .build();
    }
    @GetMapping("/my-product")
    ApiResponse<List<ProductResponse>> getMyInfo() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getMyProduct())
                .build();
    }
    @GetMapping
    public Page<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.searchProducts(name, minPrice, maxPrice, sortBy, sortDirection, page, size);
    }
    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getUser(@PathVariable("userId") Integer productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProduct(productId))
                .build();
    }
}
