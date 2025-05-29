package com.example.backend.service;

import com.example.backend.model.Product;
import com.example.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.example.backend.utils.VietnameseUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(String id) {
         return productRepository.findById(id);
    }

    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByTenspContainingIgnoreCase(name);
    }
    // Lọc sản phẩm ngẫu nhiên
    public List<Product> getRandomProducts(@Param("limit") int limit) {
        return productRepository.findRandomProducts(limit);
    }
    // Lọc sản phẩm theo danh mục
    public List<Product> getProductsByCategory(String category) {
        String normalized = VietnameseUtils.toUpperNoAccent(category);
        System.out.println(">>> Lọc sản phẩm theo category: " + normalized);
        return productRepository.findByCategory(normalized);
    }

    // Lọc sản phẩm theo khoảng giá
    public List<Product> filterProductsByPriceRange(long minPrice, long maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Sắp xếp sản phẩm theo tên
    public List<Product> sortProductsByName(boolean ascending) {
        Sort sort = ascending ? Sort.by("tensp").ascending() : Sort.by("tensp").descending();
        return productRepository.findAll(sort);
    }

    // Sắp xếp sản phẩm theo giá
    public List<Product> sortProductsByPrice(boolean ascending) {
        Sort sort = ascending ? Sort.by("price").ascending() : Sort.by("price").descending();
        return productRepository.findAll(sort);
    }

    // Thêm sản phẩm mới
    public Product addProduct(Product product) {
        if (product.getMasp() == null || product.getMasp().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm (masp) không được để trống.");
        }
        // Kiểm tra xem masp đã tồn tại chưa (tùy chọn)
        if (productRepository.findById(product.getMasp()).isPresent()) {
            throw new IllegalArgumentException("Mã sản phẩm đã tồn tại.");
        }
        return productRepository.save(product);
    }

    // Cập nhật sản phẩm
    public Product updateProduct(String id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setTensp(productDetails.getTensp());
        product.setHinhanh(productDetails.getHinhanh());
        product.setNhacungcap(productDetails.getNhacungcap());
        product.setMota(productDetails.getMota());
        product.setCategory(productDetails.getCategory());
        product.setPrice(productDetails.getPrice());
        product.setUnit(productDetails.getUnit());
        product.setStockQuantity(productDetails.getStockQuantity()); // Sửa thành getStockQuantity()

        return productRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}