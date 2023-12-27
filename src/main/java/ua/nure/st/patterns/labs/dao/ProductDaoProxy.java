package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.controller.dto.AuthDto;
import ua.nure.st.patterns.labs.entity.Product;
import ua.nure.st.patterns.labs.entity.User;

import java.util.List;

public class ProductDaoProxy implements ProductDao {

    private final ProductDao productDao;
    private final UserDao userDao;

    public ProductDaoProxy(ProductDao productDao, UserDao userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public List<Product> getAllByName(String name) {
        return productDao.getAllByName(name);
    }

    @Override
    public List<Product> getAllByBrandId(Long brandId) {
        return productDao.getAllByBrandId(brandId);
    }

    @Override
    public List<Product> getAllByCategoryId(Long categoryId) {
        return productDao.getAllByCategoryId(categoryId);
    }

    @Override
    public Product getById(Long id) {
        return productDao.getById(id);
    }

    @Override
    public boolean save(String name, String description, Long price, Long brandId, Long categoryId, AuthDto user) {
        User u = userDao.login(user.login(), user.password());
        if (u == null || u.getRoleId() != 1) {
            return false;
        }
        return productDao.save(name, description, price, brandId, categoryId, user);
    }

    @Override
    public boolean update(Product product, AuthDto auth) {
        User user = userDao.login(auth.login(), auth.password());
        if (auth == null || user.getRoleId() != 1) {
            return false;
        }
        return productDao.update(product, auth);
    }

    @Override
    public boolean undo(Long id, AuthDto user) {
        User u = userDao.login(user.login(), user.password());
        if (u == null || u.getRoleId() != 1) {
            return false;
        }
        return productDao.undo(id, user);
    }

    @Override
    public boolean delete(Long id, AuthDto user) {
        User u = userDao.login(user.login(), user.password());
        if (u == null || u.getRoleId() != 1) {
            return false;
        }
        return productDao.delete(id, user);
    }
}
