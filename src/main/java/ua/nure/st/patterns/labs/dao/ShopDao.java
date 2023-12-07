package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

public interface ShopDao<ID> {
    List<Shop<ID>> getAll();
    List<Shop<ID>> getAllByName(String name);
    Shop<ID> getById(ID id);
    boolean save(String name, String location);
    boolean update(Shop<ID> shop);
    boolean delete(ID id);
}
