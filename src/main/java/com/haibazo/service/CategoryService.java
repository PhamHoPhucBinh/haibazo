package com.haibazo.service;

import com.haibazo.dto.request.CategoryCreateRequest;
import com.haibazo.dto.request.CategoryUpdateRequest;
import com.haibazo.dto.response.CategoryResponse;
import com.haibazo.exception.AppException;
import com.haibazo.exception.ErrorCode;
import com.haibazo.mapper.CategoryMapper;
import com.haibazo.model.Category;
import com.haibazo.repository.CategoryRepository;
import com.haibazo.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<CategoryResponse> getCategoryById(Integer categoryId) {
        return Optional.ofNullable(categoryMapper.toCategoryCategoryResponse(categoryRepository.findById(categoryId).orElse(null)));
    }

    public CategoryResponse saveCategory(CategoryCreateRequest categoryCreateRequest) {
        Category category = categoryMapper.toCategory(categoryCreateRequest);
        var products = productRepository.findAllByProductNameIn(categoryCreateRequest.getProducts());
        category.setProducts(products);
        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(
                    ErrorCode.CATEGORY_EXIST);
        }
        return categoryMapper.toCategoryCategoryResponse(category);
    }


    public CategoryResponse updateCategory(Integer categoryId, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryMapper.updateCategory(category, request);
        var products = productRepository.findAllByProductNameIn(request.getProducts());
        category.setProducts(products);
        category.setCategoryName(request.getCategoryName());
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryCategoryResponse(category);
    }

    public void deleteCategory(Integer categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
    }
}
