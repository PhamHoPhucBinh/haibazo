package com.haibazo.mapper;

import com.haibazo.dto.request.CategoryCreateRequest;
import com.haibazo.dto.request.CategoryUpdateRequest;
import com.haibazo.dto.response.CategoryResponse;
import com.haibazo.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse toCategoryCategoryResponse(Category category);

    @Mapping(target = "products", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);
}
