package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Brand;

import java.util.List;

public interface BrandDao<ID> {
    List<Brand<ID>> getAll();
    Brand<ID> getById(ID id);
    boolean save(String name);
    boolean update(Brand<ID> brand);
    boolean delete(ID id);
}
