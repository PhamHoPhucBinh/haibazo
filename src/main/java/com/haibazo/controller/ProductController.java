package com.haibazo.controller;

import com.haibazo.dto.request.ProductCreateRequest;
import com.haibazo.dto.request.ProductUpdateRequest;
import com.haibazo.dto.response.ApiResponse;
import com.haibazo.dto.response.ProductResponse;
import com.haibazo.enums.Color;
import com.haibazo.enums.Size;
import com.haibazo.model.Category;
import com.haibazo.model.Product;
import com.haibazo.model.Style;
import com.haibazo.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @GetMapping("/list")
    public ApiResponse<List<Product>> getAllProducts(
            @RequestParam(required = false) Color color,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Size size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer styleId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        // Default: Get all products if no filters are provided
        if (color == null && categoryId == null && size == null && name == null && styleId == null && minPrice == null && maxPrice == null) {
            return ApiResponse.<List<Product>>builder().code(200).result(productService.getAllProducts()).build();
        }

        // Handle each filter
        if (color != null) {
            return ApiResponse.<List<Product>>builder().code(200).result(productService.getProductsByColor(color)).build();
        }

        if (categoryId != null) {
            Category category = new Category();
            category.setCategoryId(categoryId);
            return ApiResponse.<List<Product>>builder().code(200).result(productService.getProductsByCategory(category)).build();
        }

        if (size != null) {
            return ApiResponse.<List<Product>>builder().code(200).result(productService.getProductsBySize(size)).build();
        }


        if (styleId != null) {
            Style style = new Style();
            style.setStyleId(styleId);
            return ApiResponse.<List<Product>>builder().code(200).result(productService.getProductsByStyle(style)).build();
        }

        if (minPrice != null && maxPrice != null) {
            return ApiResponse.<List<Product>>builder().code(200).result(productService.getProductsByPriceRange(minPrice, maxPrice)).build();
        }

        return ApiResponse.<List<Product>>builder().code(200).result(productService.getAllProducts()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable(value = "id") Integer productId) {
        Product product = productService.getProductById(productId);
        return ApiResponse.<Product>builder().code(200).result(product).build();
    }

    @PostMapping("/create")
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreateRequest product) {
        ProductResponse productResponse = productService.saveProduct(product);
        return ApiResponse.<ProductResponse>builder().code(200).result(productResponse).build();
    }

    @PutMapping("update/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable(value = "id") Integer productId, @RequestBody ProductUpdateRequest productDetails) {
        ProductResponse productResponse = productService.updateProduct(productId, productDetails);
        return ApiResponse.<ProductResponse>builder().code(200).result(productResponse).build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable(value = "id") Integer productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<String>builder().code(200).result("Delete Product Successful").build();
    }
}
