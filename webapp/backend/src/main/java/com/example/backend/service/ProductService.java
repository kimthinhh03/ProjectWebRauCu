package com.example.backend.service;

import com.example.backend.model.Product;
import com.example.backend.model.ProductDetail;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.ProductTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private ProductTranslationRepository translationRepo;

    // Lấy tất cả sản phẩm
    public Page<Product> getAllProducts(Pageable pageable, String lang) {
        Page<Product> page = productRepository.findAll(pageable);

        // Map tên sản phẩm theo ngôn ngữ
        page.forEach(p -> {
            ProductDetail detail = p.getProductDetail();
            translationRepo.findByMaspAndLang(p.getMasp(), lang)
                    .ifPresent(tr -> detail.setTensp(tr.getName()));
        });

        return page;
    }

    // Lấy sản phẩm theo ID
    public Optional<Product> getProductById(String id, String lang) {
        Optional<Product> productOpt = productRepository.findById(id);
        productOpt.ifPresent(p -> {
            translationRepo.findByMaspAndLang(p.getMasp(), lang)
                    .ifPresent(tr -> {
                        if (p.getProductDetail() != null) {
                            p.getProductDetail().setTensp(tr.getName());
                        }
                    });
        });
        return productOpt;
    }

    // Tìm kiếm sản phẩm theo tên
    public List<Product> searchByKeyword(String keyword, String lang) {
        List<Product> products = productRepository.searchByKeyword(keyword, lang);
        applyTranslations(products, lang);
        return products;
    }

    // Lọc sản phẩm ngẫu nhiên
    public List<Product> getRandomProducts(int limit, String lang) {
        List<Product> products = productRepository.findRandomProducts(limit);
        applyTranslations(products, lang);
        return products;
    }
    // Lọc sản phẩm theo danh mục
    public List<Product> getProductsByCategory(String category, String lang) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        applyTranslations(products, lang);
        return products;
    }

    // Lọc sản phẩm theo khoảng giá
    public List<Product> filterProductsByPriceRange(long minPrice, long maxPrice, String lang) {
        List<Product> products = productRepository.findByPriceBetween(minPrice, maxPrice);
        applyTranslations(products, lang);
        return products;
    }

    // Sắp xếp sản phẩm theo tên
    public List<Product> sortProductsByName(boolean ascending, String lang) {
        List<Product> products = ascending
                ? productRepository.findAllByOrderByProductDetail_TenspAsc()
                : productRepository.findAllByOrderByProductDetail_TenspDesc();

        applyTranslations(products, lang);
        return products;
    }

    // Sắp xếp sản phẩm theo giá
    public List<Product> sortProductsByPrice(boolean ascending, String lang) {
        List<Product> products = ascending
                ? productRepository.findAllByOrderByPriceAsc()
                : productRepository.findAllByOrderByPriceDesc();

        applyTranslations(products, lang);
        return products;
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

//    // Cập nhật sản phẩm
//    public Product updateProduct(String id, Product productDetails) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
//
//        product.setTensp(productDetails.getTensp());
//        product.setHinhanh(productDetails.getHinhanh());
//        product.setNhacungcap(productDetails.getNhacungcap());
//        product.setMota(productDetails.getMota());
//        product.setCategory(productDetails.getCategory());
//        product.setPrice(productDetails.getPrice());
//        product.setUnit(productDetails.getUnit());
//        product.setStockQuantity(productDetails.getStockQuantity()); // Sửa thành getStockQuantity()
//
//        return productRepository.save(product);
//    }

    // Xóa sản phẩm
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
    private void applyTranslations(List<Product> products, String lang) {
        for (Product p : products) {
            translationRepo.findByMaspAndLang(p.getMasp(), lang)
                    .ifPresent(tr -> {
                        if (p.getProductDetail() != null) {
                            p.getProductDetail().setTensp(tr.getName());
                        }
                    });
        }
    }
}