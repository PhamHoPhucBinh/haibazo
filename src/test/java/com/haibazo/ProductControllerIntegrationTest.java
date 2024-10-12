package com.haibazo;

import com.haibazo.model.Product;
import com.haibazo.model.Category;
import com.haibazo.model.Style;
import com.haibazo.repository.CategoryRepository;
import com.haibazo.repository.ProductRepository;
import com.haibazo.repository.StyleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Rollback DB changes after each test
@Import(TestSecurityConfig.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StyleRepository styleRepository;

    @BeforeEach
    public void setup() {
        // Save the Category and Style
        Category category = new Category();
        category.setCategoryName("Clothing");
        categoryRepository.save(category);

        Style style = new Style();
        style.setStyleName("Hiphop");
        styleRepository.save(style);

        // Create and associate a Product
        Product product = new Product();
        product.setProductName("T-Shirt");
        product.setOriginalPrice(20.0);
        product.setDiscountPrice(15.0);
        product.setColor(Product.Color.GREEN);
        product.setSize(Product.Size.M);
        product.setCategory(category); // Set category (bidirectional relationship)
        product.setStyle(style); // Set style

        // Use helper method to add the product to the category
        category.addProduct(product);

        categoryRepository.save(category); // Save the category (cascades and saves the product)
    }


    // Test GET /product
    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("T-Shirt"))
                .andExpect(jsonPath("$[0].color").value("GREEN"))
                .andExpect(jsonPath("$[0].size").value("M"));
    }

    // Test GET /product/{id}
    @Test
    public void testGetProductById() throws Exception {
        Product product = productRepository.findAll().get(0);

        mockMvc.perform(get("/product/" + product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("T-Shirt"))
                .andExpect(jsonPath("$.color").value("GREEN"));
    }

    // Test POST /product (Create Product)
    @Test
    public void testCreateProduct() throws Exception {
        String newProductJson = "{ \"productName\": \"Shirt\", \"originalPrice\": 25.0, \"discountPrice\": 20.0, \"color\": \"RED\", \"size\": \"L\", \"description\": \"A nice red shirt\", \"category\": { \"categoryId\": 1 }, \"style\": { \"styleId\": 1 } }";

        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Shirt"))
                .andExpect(jsonPath("$.color").value("RED"));
    }

    // Test PUT /product/{id} (Update Product)
    @Test
    public void testUpdateProduct() throws Exception {
        Product product = productRepository.findAll().get(0);

        String updatedProductJson = "{ \"productName\": \"Updated T-Shirt\", \"originalPrice\": 22.0, \"discountPrice\": 18.0, \"color\": \"BLACK\", \"size\": \"L\", \"description\": \"Updated description\", \"category\": { \"categoryId\": 1 }, \"style\": { \"styleId\": 1 } }";

        mockMvc.perform(put("/product/" + product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Updated T-Shirt"))
                .andExpect(jsonPath("$.color").value("BLACK"));
    }

    // Test DELETE /product/{id}
    @Test
    public void testDeleteProduct() throws Exception {
        Product product = productRepository.findAll().get(0);

        mockMvc.perform(delete("/product/" + product.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
