package ua.nure.st.patterns.labs.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.controller.dto.AddProductToShopDto;
import ua.nure.st.patterns.labs.dao.ShopHasProductDao;
import ua.nure.st.patterns.labs.observer.ShopEventManager;

import java.util.List;

@RestController
@RequestMapping("/shopHasProduct")
public class ShopHasProductController {

    private final ShopHasProductDao shopHasProductDao;
    private final ShopEventManager shopEventManager;

    public ShopHasProductController(ShopHasProductDao shopHasProductDao, ShopEventManager shopEventManager) {
        this.shopHasProductDao = shopHasProductDao;
        this.shopEventManager = shopEventManager;
    }

    @GetMapping("/shopsWithProduct/{productId}")
    public List<ShopHasProductDao.ShopHasProduct> getShopsByProductId(@PathVariable Long productId) {
        return shopHasProductDao.getShopsWithProduct(productId);
    }

    @GetMapping("/productsInShop/{shopId}")
    public List<ShopHasProductDao.ProductHasShop> getProductsInShop(@PathVariable Long shopId) {
        return shopHasProductDao.getProductsInShop(shopId);
    }

    @PostMapping
    public boolean addProductToShop(@RequestBody AddProductToShopDto dto) {
        return shopHasProductDao.save(dto.shopId(), dto.productId(), dto.count());
    }

    @PutMapping
    public boolean updateProductInShop(@RequestBody AddProductToShopDto dto) {
        return shopHasProductDao.update(dto.shopId(), dto.productId(), dto.count());
    }

    @DeleteMapping("/{shopId}/{productId}")
    public boolean deleteProductFromShop(@PathVariable Long shopId, @PathVariable Long productId) {
        return shopHasProductDao.delete(shopId, productId);
    }

    @PostMapping("/subscribe/{shopId}")
    public void subscribe(@PathVariable Long shopId) {
        shopEventManager.subscribe(shopId, System.out::println);
    }
}
