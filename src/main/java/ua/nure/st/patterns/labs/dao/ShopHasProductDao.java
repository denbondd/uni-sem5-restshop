package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Product;
import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

public interface ShopHasProductDao<ID> {
    List<ProductHasShop<ID>> getProductsInShop(String shopId);
    List<ShopHasProduct<ID>> getShopsWithProduct(String productId);
    boolean save(String shopId, String productId, Integer count);
    boolean update(String shopId, String productId, Integer count);
    boolean delete(String shopId, String productId);

    record ShopHasProduct<ID>(Shop<ID> shop, Integer count) {
    }
    record ProductHasShop<ID>(Product<ID> product, Integer count) {
    }
}
