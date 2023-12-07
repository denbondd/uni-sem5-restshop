package ua.nure.st.patterns.labs.entity;

public class Brand<ID> {

    private ID id;
    private String name;

    private Brand() {}

    public Brand(ID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ID getId() {
        return id;
    }

    private void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static class Builder<ID> {

        private final Brand<ID> brand = new Brand<>();

        public Builder<ID> setId(ID id) {
            brand.setId(id);
            return this;
        }

        public Builder<ID> setName(String name) {
            brand.setName(name);
            return this;
        }

        public Brand<ID> build() {
            return brand;
        }
    }
}
