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

@CrossOrigin(origins = "http://localhost:3000")
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
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable, lang);
    }

    // Lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable String id,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        Optional<Product> product = productService.getProductById(id, lang);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Lọc lấy sản phẩm ngẫu nhiên
    @GetMapping("/random")
    public ResponseEntity<List<Product>> getRandomProducts(
            @RequestParam(defaultValue = "4") int limit,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        List<Product> products = productService.getRandomProducts(limit, lang);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(products);
    }

    // Tìm kiếm sản phẩm theo tên
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        return productService.searchByKeyword(keyword, lang);
    }

    // Lọc sản phẩm theo danh mục
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        return productService.getProductsByCategory(category, lang);
    }

    // Lọc sản phẩm theo khoảng giá
    @GetMapping("/filter")
    public List<Product> filterProductsByPrice(
            @RequestParam long minPrice,
            @RequestParam long maxPrice,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        return productService.filterProductsByPriceRange(minPrice, maxPrice, lang);
    }


    // Sắp xếp sản phẩm theo tên
    @GetMapping("/sort/name")
    public List<Product> sortProductsByName(
            @RequestParam boolean ascending,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        return productService.sortProductsByName(ascending, lang);
    }

    // Sắp xếp sản phẩm theo giá
    @GetMapping("/sort/price")
    public List<Product> sortProductsByPrice(
            @RequestParam boolean ascending,
            @RequestParam(defaultValue = "vi") String lang
    ) {
        return productService.sortProductsByPrice(ascending, lang);
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