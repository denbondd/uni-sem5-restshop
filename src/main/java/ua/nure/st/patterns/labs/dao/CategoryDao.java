package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Category;

import java.util.List;

public interface CategoryDao<ID> {
    List<Category<ID>> getAll();
    Category<ID> getById(ID id);
    boolean save(String name);
    boolean update(Category<ID> category);
    boolean delete(ID id);
}
