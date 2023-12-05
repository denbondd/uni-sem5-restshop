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

    private ProductDao productDao;
    private ShopDao shopDao;
    private BrandDao brandDao;
    private CategoryDao categoryDao;
    private ShopHasProductDao shopHasProductDao;

    public MySqlDao(DataSource dataSource, ShopEventManager shopEventManager) {
        this.dataSource = dataSource;
        this.shopEventManager = shopEventManager;
    }

    @Override
    public ShopDao getShopDao() {
        if (shopDao == null) {
            shopDao = new ShopMySqlDao(dataSource);
        }
        return shopDao;
    }

    @Override
    public ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductMySqlDao(dataSource);
        }
        return productDao;
    }

    @Override
    public BrandDao getBrandDao() {
        if (brandDao == null) {
            brandDao = new BrandMySqlDao(dataSource);
        }
        return brandDao;
    }

    @Override
    public CategoryDao getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryMySqlDao(dataSource);
        }
        return categoryDao;
    }

    @Override
    public ShopHasProductDao getShopHasProductDao() {
        if (shopHasProductDao == null) {
            shopHasProductDao = new ShopHasProductMySqlDao(dataSource, shopEventManager);
        }
        return shopHasProductDao;
    }

}
