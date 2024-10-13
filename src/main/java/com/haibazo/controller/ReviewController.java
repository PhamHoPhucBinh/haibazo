package com.haibazo.controller;

import com.haibazo.dto.request.ReviewCreateRequest;
import com.haibazo.dto.request.ReviewUpdateRequest;
import com.haibazo.dto.response.ApiResponse;
import com.haibazo.dto.response.ReviewResponse;
import com.haibazo.mapper.IReviewMapper;
import com.haibazo.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;
    IReviewMapper iReviewMapper;

    @GetMapping("/list")
    public ApiResponse<List<ReviewResponse>> getAllReviews() {
        return ApiResponse.<List<ReviewResponse>>builder().code(200)
                .result(reviewService.getAllReviews().stream().map(iReviewMapper::toReviewResponse)
                        .toList())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ReviewResponse> getReviewById(@PathVariable(value = "id") Integer reviewId) {
        ReviewResponse review = reviewService.getReviewById(reviewId);
        return ApiResponse.<ReviewResponse>builder().code(200).result(review).build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByProduct(@PathVariable(value = "productId") Integer productId) {
        return ApiResponse.<List<ReviewResponse>>builder().code(200).result(reviewService.getReviewsByProduct(productId)).build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByUser(@PathVariable(value = "userId") Integer userId) {
        return ApiResponse.<List<ReviewResponse>>builder().code(200).result(reviewService.getReviewsByUser(userId)).build();
    }

    @PostMapping("/create")
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        return ApiResponse.<ReviewResponse>builder().code(200).result(reviewService.saveReview(reviewCreateRequest)).build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<ReviewResponse> updateReview(@PathVariable(value = "id") Integer reviewId, @RequestBody ReviewUpdateRequest reviewDetails) {
        return ApiResponse.<ReviewResponse>builder().code(200).result(reviewService.updateProduct(reviewId, reviewDetails)).build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> deleteReview(@PathVariable(value = "id") Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.<String>builder().code(200).result("Delete Review Successful").build();
    }
}
