package com.example.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "chitietsanpham")
@SecondaryTable(
        name = "sanpham",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "masp")
)
public class Product implements Serializable {

    @Id
    @Column(name = "masp")
    private String masp;

    @Column(name = "tensp", table = "sanpham")
    private String tensp;

    @Column(name = "hinhanh", table = "sanpham")
    private String hinhanh;

    @Column(name = "nhacungcap", table = "sanpham")
    private String nhacungcap;

    @Column(name = "mota", table = "sanpham")
    private String mota;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price; // Sử dụng double vì price là numeric(10,2)

    @Column(name = "unit")
    private String unit;

    @Column(name = "stock_quantity")
    private Integer stock_quantity;

    public Product() {
    }
}