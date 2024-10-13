package com.haibazo.service;

import com.haibazo.dto.request.ProductCreateRequest;
import com.haibazo.dto.request.ProductUpdateRequest;
import com.haibazo.dto.response.ProductResponse;
import com.haibazo.enums.Color;
import com.haibazo.enums.Size;
import com.haibazo.exception.AppException;
import com.haibazo.exception.ErrorCode;
import com.haibazo.mapper.ProductMapper;
import com.haibazo.model.Category;
import com.haibazo.model.Product;
import com.haibazo.model.Style;
import com.haibazo.repository.CategoryRepository;
import com.haibazo.repository.ProductRepository;
import com.haibazo.repository.StyleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    StyleRepository styleRepository;
    CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    public ProductResponse updateProduct(Integer productId, ProductUpdateRequest request) {
        Product product = this.getProductById(productId);
        productMapper.update(product, request);
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    public ProductResponse saveProduct(ProductCreateRequest productCreateRequest) {
        Product product = productMapper.toProduct(productCreateRequest);
        Style style = styleRepository.findById(productCreateRequest.getStyle()).orElse(null);
        Category category = categoryRepository.findById(productCreateRequest.getCategory()).orElse(null);
        product.setStyle(style);
        product.setCategory(category);
        try {
            product = productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(
                    ErrorCode.PRODUCT_EXIST);
        }
        return productMapper.toProductResponse(product);
    }

    public void deleteProduct(Integer productId) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public List<Product> getProductsByColor(Color color) {
        return productRepository.findByColor(color);
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsBySize(Size size) {
        return productRepository.findBySize(size);
    }

    public List<Product> getProductsByStyle(Style style) {
        return productRepository.findByStyle(style);
    }

    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByDiscountPriceBetween(minPrice, maxPrice);
    }
}
