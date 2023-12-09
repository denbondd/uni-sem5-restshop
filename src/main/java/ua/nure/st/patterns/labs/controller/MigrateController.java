package ua.nure.st.patterns.labs.controller;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.dao.ShopHasProductDao;
import ua.nure.st.patterns.labs.dao.mongodb.MongoDbDao;
import ua.nure.st.patterns.labs.dao.mysql.MySqlDao;
import ua.nure.st.patterns.labs.entity.Brand;
import ua.nure.st.patterns.labs.entity.Category;
import ua.nure.st.patterns.labs.entity.Product;
import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

@RestController
@RequestMapping("/migrate")
public class MigrateController {

    private final MySqlDao mySqlDao;
    private final MongoDbDao mongoDbDao;

    public MigrateController(MySqlDao mySqlDao, MongoDbDao mongoDbDao) {
        this.mySqlDao = mySqlDao;
        this.mongoDbDao = mongoDbDao;
    }

    @PostMapping("/toMongoDB")
    public boolean migrateToMongo() {
        List<Brand<Long>> brands = mySqlDao.getBrandDao().getAll();
        List<Category<Long>> categories = mySqlDao.getCategoryDao().getAll();
        List<Product<Long>> products = mySqlDao.getProductDao().getAll();
        List<Shop<Long>> shops = mySqlDao.getShopDao().getAll();

        brands.forEach(b -> mongoDbDao.getBrandDao().save(b.getName()));
        categories.forEach(c -> mongoDbDao.getCategoryDao().save(c.getName()));
        shops.forEach(s -> mongoDbDao.getShopDao().save(s.getName(), s.getLocation()));

        List<Brand<ObjectId>> oBrands = mongoDbDao.getBrandDao().getAll();
        List<Category<ObjectId>> oCategories = mongoDbDao.getCategoryDao().getAll();
        products.forEach(p -> {
            Brand<Long> origBrand = mySqlDao.getBrandDao().getById(p.getBrandId());
            Brand<ObjectId> brand = oBrands.stream()
                    .filter(b -> b.getName().equals(origBrand.getName()))
                    .findFirst()
                    .orElseThrow();

            Category<Long> origCategory = mySqlDao.getCategoryDao().getById(p.getCategoryId());
            Category<ObjectId> category = oCategories.stream()
                    .filter(c -> c.getName().equals(origCategory.getName()))
                    .findFirst()
                    .orElseThrow();

            mongoDbDao.getProductDao()
                    .save(p.getName(), p.getPrice(), brand.getId().toString(), category.getId().toString());
        });

        shops.forEach(s -> {
            List<ShopHasProductDao.ProductHasShop<Long>> shopHasProducts = mySqlDao.getShopHasProductDao()
                    .getProductsInShop(s.getId().toString());
            shopHasProducts.forEach(shp -> {
                Shop<ObjectId> oShop = mongoDbDao.getShopDao().getAllByName(s.getName()).get(0);
                Product<ObjectId> oProduct = mongoDbDao.getProductDao().getAllByName(shp.product().getName()).get(0);
                mongoDbDao.getShopHasProductDao()
                        .save(oShop.getId().toString(), oProduct.getId().toString(), shp.count());
            });
        });

        return true;
    }

    @PostMapping("/toMySQL")
    public boolean migrateToMySQL() {
        List<Brand<ObjectId>> brands = mongoDbDao.getBrandDao().getAll();
        List<Category<ObjectId>> categories = mongoDbDao.getCategoryDao().getAll();
        List<Product<ObjectId>> products = mongoDbDao.getProductDao().getAll();
        List<Shop<ObjectId>> shops = mongoDbDao.getShopDao().getAll();

        brands.forEach(b -> mySqlDao.getBrandDao().save(b.getName()));
        categories.forEach(c -> mySqlDao.getCategoryDao().save(c.getName()));
        shops.forEach(s -> mySqlDao.getShopDao().save(s.getName(), s.getLocation()));

        List<Brand<Long>> oBrands = mySqlDao.getBrandDao().getAll();
        List<Category<Long>> oCategories = mySqlDao.getCategoryDao().getAll();
        products.forEach(p -> {
            Brand<ObjectId> origBrand = mongoDbDao.getBrandDao().getById(p.getBrandId());
            Brand<Long> brand = oBrands.stream()
                    .filter(b -> b.getName().equals(origBrand.getName()))
                    .findFirst()
                    .orElseThrow();

            Category<ObjectId> origCategory = mongoDbDao.getCategoryDao().getById(p.getCategoryId());
            Category<Long> category = oCategories.stream()
                    .filter(c -> c.getName().equals(origCategory.getName()))
                    .findFirst()
                    .orElseThrow();

            mySqlDao.getProductDao()
                    .save(p.getName(), p.getPrice(), brand.getId().toString(), category.getId().toString());
        });

        shops.forEach(s -> {
            List<ShopHasProductDao.ProductHasShop<ObjectId>> shopHasProducts = mongoDbDao.getShopHasProductDao()
                    .getProductsInShop(s.getId().toString());
            shopHasProducts.forEach(shp -> {
                Shop<Long> lShop = mySqlDao.getShopDao().getAllByName(s.getName()).get(0);
                Product<Long> lProduct = mySqlDao.getProductDao().getAllByName(shp.product().getName()).get(0);
                mySqlDao.getShopHasProductDao()
                        .save(lShop.getId().toString(), lProduct.getId().toString(), shp.count());
            });
        });

        return true;
    }
}
