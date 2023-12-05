package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Product;
import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

public interface ShopHasProductDao {
    List<ProductHasShop> getProductsInShop(Long shopId);
    List<ShopHasProduct> getShopsWithProduct(Long productId);
    boolean save(Long shopId, Long productId, Integer count);
    boolean update(Long shopId, Long productId, Integer count);
    boolean delete(Long shopId, Long productId);

    record ShopHasProduct(Shop shop, Integer count) {
    }
    record ProductHasShop(Product product, Integer count) {
    }
}
