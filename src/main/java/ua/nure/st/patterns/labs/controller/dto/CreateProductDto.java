package ua.nure.st.patterns.labs.controller.dto;

public record CreateProductDto<ID>(String name, Long price, ID brandId, ID categoryId) {
}
