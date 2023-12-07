package ua.nure.st.patterns.labs.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class Category<ID> {

    @JsonSerialize(using = ToStringSerializer.class)
    private ID id;
    private String name;

    private Category() {}

    public Category(ID id, String name) {
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

        private final Category<ID> category = new Category<>();

        public Builder<ID> setId(ID id) {
            category.setId(id);
            return this;
        }

        public Builder<ID> setName(String name) {
            category.setName(name);
            return this;
        }

        public Category<ID> build() {
            return category;
        }
    }
}
