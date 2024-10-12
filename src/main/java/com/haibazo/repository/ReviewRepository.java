package com.haibazo.repository;

import com.haibazo.model.Product;
import com.haibazo.model.Review;
import com.haibazo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByProduct(Product product);
    List<Review> findByUser(User user);
}
