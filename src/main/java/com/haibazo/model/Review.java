package com.haibazo.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Entity
@Table(name = "review", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "product_id"})})
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Enum for Rating
    public enum Rating {
        ONE, TWO, THREE, FOUR, FIVE
    }

    public Review() {
    }
    public Review(int reviewId, Rating rating, String comment, User user, Product product) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.product = product;
    }
}
