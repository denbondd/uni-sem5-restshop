package ua.nure.st.patterns.labs.dao.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.nure.st.patterns.labs.dao.BrandDao;
import ua.nure.st.patterns.labs.dao.CategoryDao;
import ua.nure.st.patterns.labs.dao.Dao;
import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.dao.ShopDao;
import ua.nure.st.patterns.labs.dao.ShopHasProductDao;

@Component
public class MongoDbDao implements Dao {

    private static String url;
    private static String dbName;

    private ShopDao<ObjectId> shopDao;
    private ProductDao<ObjectId> productDao;
    private BrandDao<ObjectId> brandDao;
    private CategoryDao<ObjectId> categoryDao;
    private ShopHasProductDao<ObjectId> shopHasProductDao;

    public MongoDbDao(@Value("${db.mongo.url}") String url, @Value("${db.mongo.name}") String dbName) {
        MongoDbDao.url = url;
        MongoDbDao.dbName = dbName;
    }

    protected static MongoClient getMongoClient() {
        ConnectionString connectionString = new ConnectionString(url);
        return MongoClients.create(connectionString);
    }

    @Override
    public ShopDao<ObjectId> getShopDao() {
        if (shopDao == null) {
            shopDao = new ShopMongoDbDao(dbName);
        }
        return shopDao;
    }

    @Override
    public ProductDao<ObjectId> getProductDao() {
        if (productDao == null) {
            productDao = new ProductMongoDbDao(dbName);
        }
        return productDao;
    }

    @Override
    public BrandDao<ObjectId> getBrandDao() {
        if (brandDao == null) {
            brandDao = new BrandMongoDbDao(dbName);
        }
        return brandDao;
    }

    @Override
    public CategoryDao<ObjectId> getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryMongoDbDao(dbName);
        }
        return categoryDao;
    }

    @Override
    public ShopHasProductDao<ObjectId> getShopHasProductDao() {
        if (shopHasProductDao == null) {
            shopHasProductDao = new ShopHasProductMongoDbDao(dbName);
        }
        return shopHasProductDao;
    }
}
