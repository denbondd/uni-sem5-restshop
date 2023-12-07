package ua.nure.st.patterns.labs.dao.mongodb;

import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.entity.Product;

import java.util.List;

public class ProductMongoDbDao implements ProductDao<ObjectId> {

    private final String dbName;

    public ProductMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public List<Product<ObjectId>> getAll() {
        return null;
    }

    @Override
    public List<Product<ObjectId>> getAllByName(String name) {
        return null;
    }

    @Override
    public List<Product<ObjectId>> getAllByBrandId(ObjectId brandId) {
        return null;
    }

    @Override
    public List<Product<ObjectId>> getAllByCategoryId(ObjectId categoryId) {
        return null;
    }

    @Override
    public Product<ObjectId> getById(ObjectId id) {
        return null;
    }

    @Override
    public boolean save(String name, String description, Long price, ObjectId brandId, ObjectId categoryId) {
        return false;
    }

    @Override
    public boolean update(Product<ObjectId> mySqlProduct) {
        return false;
    }

    @Override
    public boolean undo(ObjectId id) {
        return false;
    }

    @Override
    public boolean delete(ObjectId id) {
        return false;
    }
}
