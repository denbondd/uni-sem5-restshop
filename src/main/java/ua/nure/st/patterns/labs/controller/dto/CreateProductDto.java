package ua.nure.st.patterns.labs.controller.dto;

public record CreateProductDto(String name, String description, Long price, Long brandId, Long categoryId) {
}
