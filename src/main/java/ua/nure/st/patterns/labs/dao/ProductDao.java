package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();
    List<Product> getAllByName(String name);
    List<Product> getAllByBrandId(Long brandId);
    List<Product> getAllByCategoryId(Long categoryId);
    Product getById(Long id);
    boolean save(String name, String description, Long price, Long brandId, Long categoryId);
    boolean update(Product product);
    boolean delete(Long id);
}
