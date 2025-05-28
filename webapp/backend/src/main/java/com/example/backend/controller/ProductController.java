package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Lấy tất cả sản phẩm
    @GetMapping("/all")
    public Page<Product> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lọc lấy sản phẩm ngẫu nhiên
    @GetMapping("/random")
    public ResponseEntity<List<Product>> getRandomProducts(@RequestParam(defaultValue = "4") int limit) {
        List<Product> products = productService.getRandomProducts(limit);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products);
    }

    // Tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        return productService.searchByKeyword(keyword);
    }

    // Lọc sản phẩm theo danh mục
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }

    // Lọc sản phẩm theo khoảng giá
    @GetMapping("/filter")
    public List<Product> filterProductsByPrice(
            @RequestParam long minPrice,
            @RequestParam long maxPrice) {
        return productService.filterProductsByPriceRange(minPrice, maxPrice);
    }

    // Sắp xếp sản phẩm theo tên
    @GetMapping("/sort/name")
    public List<Product> sortProductsByName(@RequestParam boolean ascending) {
        return productService.sortProductsByName(ascending);
    }

    // Sắp xếp sản phẩm theo giá
    @GetMapping("/sort/price")
    public List<Product> sortProductsByPrice(@RequestParam boolean ascending) {
        return productService.sortProductsByPrice(ascending);
    }

    // Thêm sản phẩm mới
    @PostMapping("/addProduct")
    public Product createProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

//    // Cập nhật sản phẩm
//    @PutMapping("/{id}")
//    public ResponseEntity<Product> updateProduct(
//            @PathVariable String id,
//            @RequestBody Product productDetails) {
//
//        Product updatedProduct = productService.updateProduct(id, productDetails);
//        return ResponseEntity.ok(updatedProduct);
//    }

    // Xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}