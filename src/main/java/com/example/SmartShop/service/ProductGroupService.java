package com.example.SmartShop.service;

import com.example.SmartShop.dto.request.CreateProductGroup;
import com.example.SmartShop.dto.request.UpdateProductGroup;
import com.example.SmartShop.dto.response.ProductGroupResponse;
import com.example.SmartShop.entity.Product;
import com.example.SmartShop.entity.ProductGroup;
import com.example.SmartShop.exception.AppException;
import com.example.SmartShop.exception.ErrorCode;
import com.example.SmartShop.mapper.ProductGroupMapper;
import com.example.SmartShop.repository.ProductGroupRepository;
import com.example.SmartShop.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductGroupService {
    ProductGroupRepository productGroupRepository;
    ProductGroupMapper productGroupMapper;
    ProductRepository productRepository;

    public ProductGroupResponse createProductGroup(CreateProductGroup request) {
        if (request.getProductGroupName() == null || request.getProductGroupName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_INPUT);
        }
        if (productGroupRepository.existsByProductGroupName(request.getProductGroupName())) {
            throw new AppException(ErrorCode.PRODUCT_GROUP_EXISTED);
        }
        log.info("Creating product group with name: {}", request.getProductGroupName());
        ProductGroup productGroup = productGroupMapper.toProductGroup(request);
        return productGroupMapper.toProductGroupResponse(productGroupRepository.save(productGroup));
    }

    public ProductGroupResponse updateProductGroup(Integer productGroupId, UpdateProductGroup request) {
        ProductGroup productGroup = productGroupRepository.findByProductGroupId(productGroupId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_GROUP_NOT_EXISTED));
        if (request.getProductGroupName() != null && !request.getProductGroupName().equals(productGroup.getProductGroupName())) {
            if (productGroupRepository.existsByProductGroupName(request.getProductGroupName())) {
                throw new AppException(ErrorCode.PRODUCT_GROUP_EXISTED);
            }
        }

        log.info("Updating product group with ID: {}", productGroupId);
        productGroupMapper.updateProductGroup(productGroup, request);
        return productGroupMapper.toProductGroupResponse(productGroupRepository.save(productGroup));
    }

    public void deleteProductGroup(Integer productGroupId) {
        if (!productGroupRepository.existsByProductGroupId(productGroupId)) {
            throw new AppException(ErrorCode.PRODUCT_GROUP_NOT_EXISTED);
        }
        if (productGroupRepository.existsByProductGroupId(productGroupId)) {
            throw new AppException(ErrorCode.PRODUCT_GROUP_IN_USE);
        }

        log.info("Deleting product group with ID: {}", productGroupId);
        productGroupRepository.deleteById(productGroupId);
    }

    public ProductGroupResponse getProductGroup(Integer productGroupId) {
        log.info("Fetching product group with ID: {}", productGroupId);
        return productGroupMapper.toProductGroupResponse(
                productGroupRepository.findById(productGroupId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_GROUP_NOT_EXISTED)));
    }

    public List<Product> getProductsByGroupId(Integer productGroupId, int page, int size) {
        ProductGroup productGroup = productGroupRepository.findById(productGroupId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_GROUP_NOT_EXISTED));

        log.info("Fetching products for product group ID: {}", productGroupId);
        Pageable pageable = PageRequest.of(page, size);
        return productGroupRepository.findByProductGroupId(productGroupId, pageable).getContent();
    }

    public List<ProductGroupResponse> getProductGroups(int page, int size) {
        log.info("Fetching all product groups with page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return productGroupRepository.findAll(pageable).stream()
                .map(productGroupMapper::toProductGroupResponse)
                .collect(Collectors.toList());
    }
}