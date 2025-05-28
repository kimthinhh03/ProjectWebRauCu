package com.example.backend.repository;

import com.example.backend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    // Lấy tất cả sản phẩm
    Page<Product> findAll(Pageable pageable);

    // Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường)
    @Query("SELECT p FROM Product p WHERE LOWER(p.productDetail.tensp) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByProductName(@Param("keyword") String keyword);

    // Lọc sản phẩm theo danh mục
    List<Product> findByCategory(String category);

    // Lọc sản phẩm theo khoảng giá
    List<Product> findByPriceBetween(long minPrice, long maxPrice);

    // Lọc sản phẩm ngẫu nhiên
    @Query(value = """
    SELECT s.masp, s.tensp, s.hinhanh, s.nhacungcap, s.mota,
           c.category, c.price, c.unit, c.stock_quantity
    FROM sanpham s
    JOIN chitietsanpham c ON s.masp = c.masp
    ORDER BY RANDOM()
    LIMIT :limit
    """, nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);
}