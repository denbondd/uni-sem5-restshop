package ua.nure.st.patterns.labs.dao.mysql;

import org.springframework.stereotype.Component;
import ua.nure.st.patterns.labs.dao.BrandDao;
import ua.nure.st.patterns.labs.dao.CategoryDao;
import ua.nure.st.patterns.labs.dao.Dao;
import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.dao.ShopDao;
import ua.nure.st.patterns.labs.dao.ShopHasProductDao;
import ua.nure.st.patterns.labs.observer.ShopEventManager;

import javax.sql.DataSource;

@Component
public class MySqlDao implements Dao {

    private final DataSource dataSource;
    private final ShopEventManager shopEventManager;

    private ProductDao<Long> productDao;
    private ShopDao<Long> shopDao;
    private BrandDao<Long> brandDao;
    private CategoryDao<Long> categoryDao;
    private ShopHasProductDao<Long> shopHasProductDao;

    public MySqlDao(DataSource dataSource, ShopEventManager shopEventManager) {
        this.dataSource = dataSource;
        this.shopEventManager = shopEventManager;
    }

    @Override
    public ShopDao<Long> getShopDao() {
        if (shopDao == null) {
            shopDao = new ShopMySqlDao(dataSource);
        }
        return shopDao;
    }

    @Override
    public ProductDao<Long> getProductDao() {
        if (productDao == null) {
            productDao = new ProductMySqlDao(dataSource);
        }
        return productDao;
    }

    @Override
    public BrandDao<Long> getBrandDao() {
        if (brandDao == null) {
            brandDao = new BrandMySqlDao(dataSource);
        }
        return brandDao;
    }

    @Override
    public CategoryDao<Long> getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryMySqlDao(dataSource);
        }
        return categoryDao;
    }

    @Override
    public ShopHasProductDao<Long> getShopHasProductDao() {
        if (shopHasProductDao == null) {
            shopHasProductDao = new ShopHasProductMySqlDao(dataSource, shopEventManager);
        }
        return shopHasProductDao;
    }

}
