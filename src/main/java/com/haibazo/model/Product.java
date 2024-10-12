package com.haibazo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "original_price")
    private double originalPrice;
    @Column(name = "discount_price")
    private double discountPrice;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Lob
    private String description;
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    private Style style;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // One-to-many relationship with reviews
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    // Enum for Color
    public enum Color {
        GREEN, RED, PURPLE, BLACK
    }

    // Enum for Size
    public enum Size {
        S, M, L, XL, XXL
    }

    public Product() {
    }

    public Product(Integer productId, String productName, double originalPrice, double discountPrice, Color color, Size size, String description, String imageUrl, Style style, Category category, List<Review> reviews) {
        this.productId = productId;
        this.productName = productName;
        this.originalPrice = originalPrice;
        this.discountPrice = discountPrice;
        this.color = color;
        this.size = size;
        this.description = description;
        this.imageUrl = imageUrl;
        this.style = style;
        this.category = category;
        this.reviews = reviews;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
