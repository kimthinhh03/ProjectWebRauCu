package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chitietsanpham")
public class Product implements Serializable {

    @Id
    @Column(name = "masp")
    private String masp;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "stock_quantity")
    private Integer stock_quantity;

    // Liên kết với bảng sanpham
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "masp", referencedColumnName = "masp", insertable = false, updatable = false)
    private ProductDetail productDetail;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private java.util.List<ProductTranslation> translations;
}
