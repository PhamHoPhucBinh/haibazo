package com.haibazo.service;

import com.haibazo.model.Product;
import com.haibazo.model.Category;
import com.haibazo.model.Style;
import com.haibazo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
    public List<Product> getProductsByColor(Product.Color color) {
        return productRepository.findByColor(color);
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsBySize(Product.Size size) {
        return productRepository.findBySize(size);
    }

    public List<Product> getProductsByStyle(Style style) {
        return productRepository.findByStyle(style);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByDiscountPriceBetween(minPrice, maxPrice);
    }
}
