package ua.nure.st.patterns.labs.dao.mongodb;

import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.BrandDao;
import ua.nure.st.patterns.labs.entity.Brand;

import java.util.List;

public class BrandMongoDbDao implements BrandDao<ObjectId> {

    private final String dbName;

    public BrandMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public List<Brand<ObjectId>> getAll() {
        return null;
    }

    @Override
    public Brand<ObjectId> getById(ObjectId id) {
        return null;
    }

    @Override
    public boolean save(String name) {
        return false;
    }

    @Override
    public boolean update(Brand<ObjectId> brand) {
        return false;
    }

    @Override
    public boolean delete(ObjectId id) {
        return false;
    }
}
