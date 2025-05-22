package com.example.backend.repository;

import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    List<Product> findByTenspContainingIgnoreCase(String name);

    // Lọc sản phẩm theo danh mục
    List<Product> findByCategory(String category);

    // Lọc sản phẩm theo khoảng giá
    List<Product> findByPriceBetween(long minPrice, long maxPrice);
}