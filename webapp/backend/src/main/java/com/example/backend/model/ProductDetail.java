package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sanpham")
public class ProductDetail {

    @Id
    @Column(name = "masp")
    private String masp;

    @Transient
    private String tensp;

    @Column(name = "hinhanh")
    private String hinhanh;

    @Column(name = "nhacungcap")
    private String nhacungcap;

    @Column(name = "mota")
    private String mota;
}
