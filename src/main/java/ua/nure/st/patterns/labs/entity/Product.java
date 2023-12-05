package ua.nure.st.patterns.labs.entity;

public class Product {
    private Long id;
    private Long brandId;
    private Long categoryId;
    private String name;
    private Long price;

    private Product() {}

    public Product(Long id, Long brandId, Long categoryId, String name, Long price) {
        this.id = id;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public static class Builder {
        private final Product product = new Product();

        public Builder setId(Long id) {
            product.setId(id);
            return this;
        }

        public Builder setBrandId(Long brandId) {
            product.setBrandId(brandId);
            return this;
        }

        public Builder setCategoryId(Long categoryId) {
            product.setCategoryId(categoryId);
            return this;
        }

        public Builder setName(String name) {
            product.setName(name);
            return this;
        }

        public Builder setPrice(Long price) {
            product.setPrice(price);
            return this;
        }

        public Product build() {
            return product;
        }
    }
}
