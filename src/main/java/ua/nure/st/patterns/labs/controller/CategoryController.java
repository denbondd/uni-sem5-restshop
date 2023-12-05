package ua.nure.st.patterns.labs.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.controller.dto.CreateWithNameDto;
import ua.nure.st.patterns.labs.dao.CategoryDao;
import ua.nure.st.patterns.labs.entity.Category;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryDao categoryDao;

    public CategoryController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryDao.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryDao.getById(id);
    }

    @PostMapping
    public boolean addCategory(@RequestBody CreateWithNameDto dto) {
        return categoryDao.save(dto.name());
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category) {
        categoryDao.update(category);
        return category;
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryDao.delete(id);
    }
}
