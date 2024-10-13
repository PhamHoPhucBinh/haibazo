package com.haibazo.mapper;

import com.haibazo.dto.request.ProductCreateRequest;
import com.haibazo.dto.request.ProductUpdateRequest;
import com.haibazo.dto.response.ProductResponse;
import com.haibazo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "style", ignore = true)
    Product toProduct(ProductCreateRequest productCreateRequest);

    ProductResponse toProductResponse(Product product);

    void update(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);

}
