package ua.nure.st.patterns.labs.dao.mongodb;

import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.ShopHasProductDao;

import java.util.List;

public class ShopHasProductMongoDbDao implements ShopHasProductDao<ObjectId> {

    private final String dbName;

    public ShopHasProductMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public List<ProductHasShop<ObjectId>> getProductsInShop(ObjectId shopId) {
        return null;
    }

    @Override
    public List<ShopHasProduct<ObjectId>> getShopsWithProduct(ObjectId productId) {
        return null;
    }

    @Override
    public boolean save(ObjectId shopId, ObjectId productId, Integer count) {
        return false;
    }

    @Override
    public boolean update(ObjectId shopId, ObjectId productId, Integer count) {
        return false;
    }

    @Override
    public boolean delete(ObjectId shopId, ObjectId productId) {
        return false;
    }
}
