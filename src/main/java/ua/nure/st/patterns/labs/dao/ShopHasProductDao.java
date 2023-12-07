package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.Product;
import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

public interface ShopHasProductDao<ID> {
    List<ProductHasShop<ID>> getProductsInShop(ID shopId);
    List<ShopHasProduct<ID>> getShopsWithProduct(ID productId);
    boolean save(ID shopId, ID productId, Integer count);
    boolean update(ID shopId, ID productId, Integer count);
    boolean delete(ID shopId, ID productId);

    record ShopHasProduct<ID>(Shop<ID> shop, Integer count) {
    }
    record ProductHasShop<ID>(Product<ID> product, Integer count) {
    }
}
