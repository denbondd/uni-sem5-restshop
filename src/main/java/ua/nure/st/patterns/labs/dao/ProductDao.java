package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Product;

import java.util.List;

public interface ProductDao<ID> {
    List<Product<ID>> getAll();
    List<Product<ID>> getAllByName(String name);
    List<Product<ID>> getAllByBrandId(String brandId);
    List<Product<ID>> getAllByCategoryId(String categoryId);
    Product<ID> getById(String id);
    boolean save(String name, String description, Long price, String brandId, String categoryId);
    boolean update(Product<ID> product);
    boolean undo(String id);
    boolean delete(String id);
}
