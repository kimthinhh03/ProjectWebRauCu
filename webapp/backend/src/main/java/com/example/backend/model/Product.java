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
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "masp", referencedColumnName = "masp")
)
public class Product implements Serializable {

    @Id
    @Column(name = "masp")
    private String masp;

    // Các thuộc tính từ secondary table (sanpham)
    @Column(name = "tensp", table = "sanpham")
    private String tensp;

    @Column(name = "hinhanh", table = "sanpham")
    private String hinhanh;

    @Column(name = "nhacungcap", table = "sanpham")
    private String nhacungcap;

    @Column(name = "mota", table = "sanpham")
    private String mota;

    // Các thuộc tính từ primary table (chitietsanpham)
    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;


    public Product() {
    }
}