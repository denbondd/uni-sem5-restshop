package ua.nure.st.patterns.labs.entity;

public class Brand {

    private Long id;
    private String name;

    private Brand() {}

    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static class Builder {

        private final Brand brand = new Brand();

        public Builder setId(Long id) {
            brand.setId(id);
            return this;
        }

        public Builder setName(String name) {
            brand.setName(name);
            return this;
        }

        public Brand build() {
            return brand;
        }
    }
}
