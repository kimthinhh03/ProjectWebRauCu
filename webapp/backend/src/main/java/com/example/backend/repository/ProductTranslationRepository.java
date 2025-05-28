package com.example.backend.repository;

import com.example.backend.model.ProductTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Long> {
    Optional<ProductTranslation> findByMaspAndLang(String masp, String lang);
}
