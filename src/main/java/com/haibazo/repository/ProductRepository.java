package com.haibazo.repository;

import com.haibazo.enums.Color;
import com.haibazo.enums.Size;
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

    List<Product> findByColor(Color color);

    List<Product> findBySize(Size size);

    List<Product> findByDiscountPriceBetween(double minPrice, double maxPrice);

    List<Product> findAllByProductNameIn(List<String> productNames);
}
