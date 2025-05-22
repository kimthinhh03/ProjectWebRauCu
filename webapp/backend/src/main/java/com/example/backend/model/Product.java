package com.example.backend.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SANPHAM")
@SecondaryTable(
        name = "CTSANPHAM",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "MaSP")
)
public class Product implements Serializable {

    @Id
    @Column(name = "MaSP")
    private String maSP;

    @Column(name = "TenSP", table = "SANPHAM")
    private String nameProduct;

    @Column(name = "TenFileSP", table = "SANPHAM")
    private String nameFile;

    @Column(name = "ThuongHieu", table = "SANPHAM")
    private String brandName;

    @Column(name = "Mota", table = "SANPHAM")
    private String description;

    @Column(name = "LoaiSP", table = "CTSANPHAM")
    private String typeProduct;

    @Column(name = "Gia", table = "CTSANPHAM")
    private long price;

    @Column(name = "DonViTinh", table = "CTSANPHAM")
    private String unit;

    @Column(name = "Soluong", table = "CTSANPHAM")
    private int quantity;

    public Product() {}

    public Product(String maSP, String nameProduct, String nameFile, String brandName,
                   String description, String typeProduct, long price, String unit, int quantity) {
        this.maSP = maSP;
        this.nameProduct = nameProduct;
        this.nameFile = nameFile;
        this.brandName = brandName;
        this.description = description;
        this.typeProduct = typeProduct;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Override equals & hashCode (bắt buộc nếu Product làm key trong Map)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(maSP, product.maSP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSP);
    }
}
