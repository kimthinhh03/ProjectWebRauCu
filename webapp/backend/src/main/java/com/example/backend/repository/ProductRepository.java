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
    @Query("SELECT p FROM Product p JOIN ProductTranslation t ON p.masp = t.masp " +
            "WHERE t.lang = :lang AND LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword, @Param("lang") String lang);

    // Lọc sản phẩm theo danh mục
    List<Product> findByCategoryIgnoreCase(String category);

    // Lọc sản phẩm theo khoảng giá
    List<Product> findByPriceBetween(long minPrice, long maxPrice);

    // Lọc sản phẩm ngẫu nhiên
    @Query(value = "SELECT * FROM product ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Product> findRandomProducts(@Param("limit") int limit);

    // Sắp xếp sản phẩm
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByProductDetail_TenspAsc();
    List<Product> findAllByOrderByProductDetail_TenspDesc();
}