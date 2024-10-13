package com.haibazo.service;

import com.haibazo.dto.request.ReviewCreateRequest;
import com.haibazo.dto.request.ReviewUpdateRequest;
import com.haibazo.dto.response.ReviewResponse;
import com.haibazo.exception.AppException;
import com.haibazo.exception.ErrorCode;
import com.haibazo.mapper.IReviewMapper;
import com.haibazo.model.Product;
import com.haibazo.model.Review;
import com.haibazo.model.User;
import com.haibazo.repository.ProductRepository;
import com.haibazo.repository.ReviewRepository;
import com.haibazo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewService {

    ReviewRepository reviewRepository;
    IReviewMapper iReviewMapper;
    ProductRepository productRepository;
    UserRepository userRepository;


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public ReviewResponse getReviewById(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        return iReviewMapper.toReviewResponse(review);
    }

    public List<ReviewResponse> getReviewsByProduct(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<Review> review = reviewRepository.findByProduct(product);
        return review.stream().map(iReviewMapper::toReviewResponse).toList();
    }

    public List<ReviewResponse> getReviewsByUser(Integer userid) {
        User user = userRepository.findById(userid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Review> review = reviewRepository.findByUser(user);
        return review.stream().map(iReviewMapper::toReviewResponse).toList();
    }

    public ReviewResponse saveReview(ReviewCreateRequest reviewCreateRequest) {
        Review review = iReviewMapper.toReview(reviewCreateRequest);
        review.setUser(userRepository.findById(reviewCreateRequest.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        review.setProduct(productRepository.findById(reviewCreateRequest.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)));
        review = reviewRepository.save(review);
        return iReviewMapper.toReviewResponse(review);
    }

    public ReviewResponse updateProduct(Integer reviewId, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));
        iReviewMapper.updateReview(review, request);
        review = reviewRepository.save(review);
        return iReviewMapper.toReviewResponse(review);
    }

    public void deleteReview(Integer reviewId) {
        try {
            reviewRepository.deleteById(reviewId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND);
        }
    }
}
