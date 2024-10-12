package com.haibazo.controller;

import com.haibazo.model.Category;
import com.haibazo.model.Product;
import com.haibazo.model.Style;
import com.haibazo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) Product.Color color,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Product.Size size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer styleId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        // Default: Get all products if no filters are provided
        if (color == null && categoryId == null && size == null && name == null && styleId == null && minPrice == null && maxPrice == null) {
            return productService.getAllProducts();
        }

        // Handle each filter
        if (color != null) {
            return productService.getProductsByColor(color);
        }

        if (categoryId != null) {
            Category category = new Category();
            category.setCategoryId(categoryId);
            return productService.getProductsByCategory(category);
        }

        if (size != null) {
            return productService.getProductsBySize(size);
        }


        if (styleId != null) {
            Style style = new Style();
            style.setStyleId(styleId);
            return productService.getProductsByStyle(style);
        }

        if (minPrice != null && maxPrice != null) {
            return productService.getProductsByPriceRange(minPrice, maxPrice);
        }

        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Optional<Product> product = productService.getProductById(productId);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody Product productDetails) {
        Optional<Product> product = productService.getProductById(productId);
        if (product.isPresent()) {
            productDetails.setProductId(productId);
            return ResponseEntity.ok(productService.saveProduct(productDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
