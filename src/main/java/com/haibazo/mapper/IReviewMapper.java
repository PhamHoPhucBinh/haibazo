package com.haibazo.mapper;

import com.haibazo.dto.request.ReviewCreateRequest;
import com.haibazo.dto.request.ReviewUpdateRequest;
import com.haibazo.dto.response.ReviewResponse;
import com.haibazo.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IReviewMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    Review toReview(ReviewCreateRequest reviewCreateRequest);

    ReviewResponse toReviewResponse(Review review);


    void updateReview(@MappingTarget Review review, ReviewUpdateRequest reviewUpdateRequest);
}
