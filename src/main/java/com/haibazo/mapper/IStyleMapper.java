package com.haibazo.mapper;

import com.haibazo.dto.request.StyleCreateRequest;
import com.haibazo.dto.request.StyleUpdateRequest;
import com.haibazo.dto.response.StyleResponse;
import com.haibazo.model.Style;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IStyleMapper {
    Style toStyle(StyleCreateRequest reviewCreateRequest);

    StyleResponse toStyleRepository(Style style);


    void updateStyle(@MappingTarget Style style, StyleUpdateRequest styleUpdateRequest);

}
