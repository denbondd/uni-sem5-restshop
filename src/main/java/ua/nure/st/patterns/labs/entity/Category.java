package ua.nure.st.patterns.labs.entity;

public class Category {

    private Long id;
    private String name;

    private Category() {}

    public Category(Long id, String name) {
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

        private final Category category = new Category();

        public Builder setId(Long id) {
            category.setId(id);
            return this;
        }

        public Builder setName(String name) {
            category.setName(name);
            return this;
        }

        public Category build() {
            return category;
        }
    }
}
