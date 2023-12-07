package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Product;

import java.util.List;

public interface ProductDao<ID> {
    List<Product<ID>> getAll();
    List<Product<ID>> getAllByName(String name);
    List<Product<ID>> getAllByBrandId(ID brandId);
    List<Product<ID>> getAllByCategoryId(ID categoryId);
    Product<ID> getById(ID id);
    boolean save(String name, String description, Long price, ID brandId, ID categoryId);
    boolean update(Product<ID> product);
    boolean undo(ID id);
    boolean delete(ID id);
}
