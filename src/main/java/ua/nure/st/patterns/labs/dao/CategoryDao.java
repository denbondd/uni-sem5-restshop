package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAll();
    Category getById(Long id);
    boolean save(String name);
    boolean update(Category category);
    boolean delete(Long id);
}
