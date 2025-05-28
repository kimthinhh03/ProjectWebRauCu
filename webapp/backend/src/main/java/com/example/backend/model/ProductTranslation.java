package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_translation")
public class ProductTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String masp;
    private String lang;
    private String name;


    public ProductTranslation() {}

    public ProductTranslation(String masp, String lang, String name) {
        this.masp = masp;
        this.lang = lang;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
