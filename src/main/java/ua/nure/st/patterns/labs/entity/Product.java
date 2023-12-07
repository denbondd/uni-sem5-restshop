package ua.nure.st.patterns.labs.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Product<ID> {

    @JsonSerialize(using = ToStringSerializer.class)
    private ID id;
    private Long brandId;
    private Long categoryId;
    private String name;
    private Long price;

    private Product() {}

    public Product(ID id, Long brandId, Long categoryId, String name, Long price) {
        this.id = id;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public static class Builder<ID> {
        private final Product<ID> product = new Product<>();

        public Builder<ID> setId(ID id) {
            product.setId(id);
            return this;
        }

        public Builder<ID> setBrandId(Long brandId) {
            product.setBrandId(brandId);
            return this;
        }

        public Builder<ID> setCategoryId(Long categoryId) {
            product.setCategoryId(categoryId);
            return this;
        }

        public Builder<ID> setName(String name) {
            product.setName(name);
            return this;
        }

        public Builder<ID> setPrice(Long price) {
            product.setPrice(price);
            return this;
        }

        public Product<ID> build() {
            return product;
        }
    }
}
