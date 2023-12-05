package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Brand;

import java.util.List;

public interface BrandDao {
    List<Brand> getAll();
    Brand getById(Long id);
    boolean save(String name);
    boolean update(Brand brand);
    boolean delete(Long id);
}
