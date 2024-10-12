package com.haibazo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.IdGeneratorType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_id")
    private int styleId;
    @Column(name = "style_name")
    private String styleName;
    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>(); ;

    public void addProduct(Product product) {
        products.add(product);
        product.setStyle(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setStyle(null);
    }

    public Style() {
    }

    public Style(int styleId, String styleName, List<Product> products) {
        this.styleId = styleId;
        this.styleName = styleName;
        this.products = products;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
