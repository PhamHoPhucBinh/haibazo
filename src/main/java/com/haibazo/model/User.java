package com.haibazo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    private String username;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public User() {
    }

    public User(Integer userId, String username, String email, String password, List<Review> reviews) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.reviews = reviews;
    }
}
