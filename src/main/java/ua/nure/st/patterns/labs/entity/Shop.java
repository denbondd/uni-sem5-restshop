package ua.nure.st.patterns.labs.entity;

public class Shop<ID> {

    private ID id;
    private String name;
    private String location;

    private Shop() {}

    public Shop(ID id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    public static class Builder<ID> {
        private final Shop<ID> shop = new Shop<>();

        public Builder<ID> setId(ID id) {
            shop.setId(id);
            return this;
        }

        public Builder<ID> setName(String name) {
            shop.setName(name);
            return this;
        }

        public Builder<ID> setLocation(String location) {
            shop.setLocation(location);
            return this;
        }

        public Shop<ID> build() {
            return shop;
        }
    }
}
