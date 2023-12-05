package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

public interface ShopDao {
    List<Shop> getAll();
    List<Shop> getAllByName(String name);
    Shop getById(Long id);
    boolean save(String name, String location);
    boolean update(Shop shop);
    boolean delete(Long id);
}
