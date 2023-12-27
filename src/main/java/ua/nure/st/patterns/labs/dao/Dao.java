package ua.nure.st.patterns.labs.dao;

public interface Dao {
    ShopDao getShopDao();
    ProductDao getProductDao();
    BrandDao getBrandDao();
    CategoryDao getCategoryDao();
    ShopHasProductDao getShopHasProductDao();
    UserDao getUserDao();
}
