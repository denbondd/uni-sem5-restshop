package ua.nure.st.patterns.labs.dao.mongodb;

import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.CategoryDao;
import ua.nure.st.patterns.labs.entity.Category;

import java.util.List;

public class CategoryMongoDbDao implements CategoryDao<ObjectId> {

    private final String dbName;

    public CategoryMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public List<Category<ObjectId>> getAll() {
        return null;
    }

    @Override
    public Category<ObjectId> getById(ObjectId id) {
        return null;
    }

    @Override
    public boolean save(String name) {
        return false;
    }

    @Override
    public boolean update(Category<ObjectId> category) {
        return false;
    }

    @Override
    public boolean delete(ObjectId id) {
        return false;
    }
}
