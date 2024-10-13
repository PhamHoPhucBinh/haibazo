package com.haibazo.controller;

import com.haibazo.dto.request.CategoryCreateRequest;
import com.haibazo.dto.request.CategoryUpdateRequest;
import com.haibazo.dto.response.ApiResponse;
import com.haibazo.dto.response.CategoryResponse;
import com.haibazo.model.Category;
import com.haibazo.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @GetMapping("/list")
    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.<List<Category>>builder().code(200).result(categoryService.getAllCategories()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Optional<CategoryResponse>> getCategoryById(@PathVariable(value = "id") Integer categoryId) {
        Optional<CategoryResponse> categoryResponse = categoryService.getCategoryById(categoryId);
        if (categoryResponse.isPresent()) {
            return ApiResponse.<Optional<CategoryResponse>>builder().code(200).result(categoryResponse).build();
        }
        return ApiResponse.<Optional<CategoryResponse>>builder().code(404).message("Category Is Not Found").build();
    }

    @PostMapping("/create")
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        CategoryResponse categoryResponse = categoryService.saveCategory(categoryCreateRequest);
        return ApiResponse.<CategoryResponse>builder().code(200).result(categoryResponse).build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable(value = "id") Integer categoryId,
                                                        @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, categoryUpdateRequest);
        return ApiResponse.<CategoryResponse>builder().code(200).result(categoryResponse).build();

    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable(value = "id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder().code(200).result("Category deleted successfully").build();
    }
}
