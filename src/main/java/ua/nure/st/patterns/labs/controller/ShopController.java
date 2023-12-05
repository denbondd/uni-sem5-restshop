package ua.nure.st.patterns.labs.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.controller.dto.CreateShopDto;
import ua.nure.st.patterns.labs.dao.ShopDao;
import ua.nure.st.patterns.labs.entity.Shop;

import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private final ShopDao shopDao;

    public ShopController(ShopDao shopDao) {
        this.shopDao = shopDao;
    }

    @GetMapping
    public List<Shop> getShops() {
        return shopDao.getAll();
    }

    @GetMapping("/{id}")
    public Shop getShop(@PathVariable Long id) {
        return shopDao.getById(id);
    }

    @GetMapping("/name/{name}")
    public List<Shop> getShopsByName(@PathVariable String name) {
        return shopDao.getAllByName(name);
    }

    @PostMapping
    public boolean addShop(@RequestBody CreateShopDto dto) {
        return shopDao.save(dto.name(), dto.location());
    }

    @PutMapping("/{id}")
    public Shop updateShop(@RequestBody Shop shop) {
        shopDao.update(shop);
        return shop;
    }

    @DeleteMapping("/{id}")
    public void deleteShop(@PathVariable Long id) {
        shopDao.delete(id);
    }
}
