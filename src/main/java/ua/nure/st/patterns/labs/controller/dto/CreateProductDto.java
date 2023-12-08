package ua.nure.st.patterns.labs.controller.dto;

public record CreateProductDto<ID>(String name, String description, Long price, ID brandId, ID categoryId) {
}
