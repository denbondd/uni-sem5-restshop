package ua.nure.st.patterns.labs.entity;

public class Shop {

    private Long id;
    private String name;
    private String location;

    private Shop() {}

    public Shop(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    public static class Builder {
        private final Shop shop = new Shop();

        public Builder setId(Long id) {
            shop.setId(id);
            return this;
        }

        public Builder setName(String name) {
            shop.setName(name);
            return this;
        }

        public Builder setLocation(String location) {
            shop.setLocation(location);
            return this;
        }

        public Shop build() {
            return shop;
        }
    }
}
