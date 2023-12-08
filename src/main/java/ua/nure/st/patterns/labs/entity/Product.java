package ua.nure.st.patterns.labs.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Product<ID> {

    @JsonSerialize(using = ToStringSerializer.class)
    private ID id;
    @JsonSerialize(using = ToStringSerializer.class)
    private ID brandId;
    @JsonSerialize(using = ToStringSerializer.class)
    private ID categoryId;
    private String name;
    private Long price;

    private Product() {}

    public Product(ID id, ID brandId, ID categoryId, String name, Long price) {
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

    public ID getBrandId() {
        return brandId;
    }

    public void setBrandId(ID brandId) {
        this.brandId = brandId;
    }

    public ID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ID categoryId) {
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

        public Builder<ID> setBrandId(ID brandId) {
            product.setBrandId(brandId);
            return this;
        }

        public Builder<ID> setCategoryId(ID categoryId) {
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
