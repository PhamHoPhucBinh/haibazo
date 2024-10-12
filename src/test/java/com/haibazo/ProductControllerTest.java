package com.haibazo;

import com.haibazo.controller.ProductController;
import com.haibazo.model.Category;
import com.haibazo.model.Product;
import com.haibazo.model.Style;
import com.haibazo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    // Test for getAllProducts
    @Test
    public void testGetAllProducts_noFilters() throws Exception {
        // Arrange
        List<Product> mockProducts = Arrays.asList(
                new Product(1, "Product 1", 100.0, 90.0, Product.Color.GREEN, Product.Size.M, "Description 1", "imageUrl1", new Style(), new Category(), null),
                new Product(2, "Product 2", 150.0, 120.0, Product.Color.RED, Product.Size.L, "Description 2", "imageUrl2", new Style(), new Category(), null)
        );
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Act & Assert
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk());

        verify(productService, times(1)).getAllProducts();
    }

    // Test for getProductById - product exists
    @Test
    public void testGetProductById_productExists() throws Exception {
        // Arrange
        Product mockProduct = new Product(1, "Product 1", 100.0, 90.0, Product.Color.GREEN, Product.Size.M, "Description 1", "imageUrl1", new Style(), new Category(), null);
        when(productService.getProductById(1)).thenReturn(Optional.of(mockProduct));

        // Act
        ResponseEntity<Product> response = productController.getProductById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productService, times(1)).getProductById(1);
    }

    // Test for getProductById - product does not exist
    @Test
    public void testGetProductById_productNotFound() throws Exception {
        // Arrange
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.getProductById(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).getProductById(1);
    }

    // Test for createProduct
    @Test
    public void testCreateProduct() {
        // Arrange
        Product mockProduct = new Product(1, "Product 1", 100.0, 90.0, Product.Color.GREEN, Product.Size.M, "Description 1", "imageUrl1", new Style(), new Category(), null);
        when(productService.saveProduct(any(Product.class))).thenReturn(mockProduct);

        // Act
        Product createdProduct = productController.createProduct(mockProduct);

        // Assert
        assertEquals(mockProduct, createdProduct);
        verify(productService, times(1)).saveProduct(mockProduct);
    }

    // Test for updateProduct - product exists
    @Test
    public void testUpdateProduct_productExists() {
        // Arrange
        Product mockProduct = new Product(1, "Updated Product", 120.0, 100.0, Product.Color.RED, Product.Size.L, "Updated Description", "imageUrl2", new Style(), new Category(), null);
        when(productService.getProductById(1)).thenReturn(Optional.of(mockProduct));
        when(productService.saveProduct(any(Product.class))).thenReturn(mockProduct);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(1, mockProduct);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productService, times(1)).getProductById(1);
        verify(productService, times(1)).saveProduct(mockProduct);
    }

    // Test for updateProduct - product does not exist
    @Test
    public void testUpdateProduct_productNotFound() {
        // Arrange
        Product mockProduct = new Product(1, "Updated Product", 120.0, 100.0, Product.Color.RED, Product.Size.L, "Updated Description", "imageUrl2", new Style(), new Category(), null);
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Product> response = productController.updateProduct(1, mockProduct);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).getProductById(1);
        verify(productService, never()).saveProduct(any(Product.class));
    }

    // Test for deleteProduct
    @Test
    public void testDeleteProduct() {
        // Act
        ResponseEntity<Void> response = productController.deleteProduct(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1);
    }
}
