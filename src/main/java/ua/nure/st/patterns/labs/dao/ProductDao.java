package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.controller.dto.AuthDto;
import ua.nure.st.patterns.labs.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    List<Product> getAllByName(String name);
    List<Product> getAllByBrandId(Long brandId);
    List<Product> getAllByCategoryId(Long categoryId);
    Product getById(Long id);
    boolean save(String name, String description, Long price, Long brandId, Long categoryId, AuthDto user);
    boolean update(Product product, AuthDto user);
    boolean undo(Long id, AuthDto user);
    boolean delete(Long id, AuthDto user);
}
