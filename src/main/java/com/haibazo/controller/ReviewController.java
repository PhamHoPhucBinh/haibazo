package com.haibazo.controller;

import com.haibazo.model.Product;
import com.haibazo.model.Review;
import com.haibazo.model.User;
import com.haibazo.service.ReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController {

    ReviewService reviewService;

    @GetMapping("/list")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable(value = "id") Integer reviewId) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        return review.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public List<Review> getReviewsByProduct(@PathVariable(value = "productId") Product productId) {
        return reviewService.getReviewsByProduct(productId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable(value = "userId") User userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable(value = "id") Integer reviewId, @RequestBody Review reviewDetails) {
        Optional<Review> review = reviewService.getReviewById(reviewId);
        if (review.isPresent()) {
            reviewDetails.setReviewId(reviewId);
            return ResponseEntity.ok(reviewService.saveReview(reviewDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable(value = "id") Integer reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
