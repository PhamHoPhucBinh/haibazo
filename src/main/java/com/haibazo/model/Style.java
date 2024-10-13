package com.haibazo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.IdGeneratorType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_id")
    private int styleId;
    @Column(name = "style_name")
    private String styleName;
    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public void addProduct(Product product) {
        products.add(product);
        product.setStyle(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setStyle(null);
    }


}
