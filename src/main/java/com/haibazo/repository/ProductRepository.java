package com.haibazo.repository;

import com.haibazo.model.Category;
import com.haibazo.model.Product;
import com.haibazo.model.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

    List<Product> findByStyle(Style style);

    List<Product> findByColor(Product.Color color);

    List<Product> findBySize(Product.Size size);

    List<Product> findByDiscountPriceBetween(double minPrice, double maxPrice);
}
