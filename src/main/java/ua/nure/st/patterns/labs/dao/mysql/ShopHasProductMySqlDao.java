package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.dao.ShopHasProductDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ua.nure.st.patterns.labs.dao.mysql.ProductMySqlDao.mapRsToProduct;
import static ua.nure.st.patterns.labs.dao.mysql.ShopMySqlDao.mapRsToShop;

public class ShopHasProductMySqlDao implements ShopHasProductDao {
    private static final String SELECT_PRODUCTS_IN_SHOP = "SELECT * FROM shop_has_product JOIN product ON shop_has_product.product_id = product.id WHERE shop_id = ?";
    private static final String SELECT_SHOPS_WITH_PRODUCT = "SELECT * FROM shop_has_product JOIN shop ON shop_has_product.shop_id = shop.id WHERE product_id = ?";
    private static final String INSERT = "INSERT INTO shop_has_product (shop_id, product_id, count) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE shop_has_product SET count = ? WHERE shop_id = ? AND product_id = ?";
    private static final String DELETE = "DELETE FROM shop_has_product WHERE shop_id = ? AND product_id = ?";

    private final DataSource dataSource;

    public ShopHasProductMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<ProductHasShop> getProductsInShop(Long shopId) {
        List<ProductHasShop> products = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PRODUCTS_IN_SHOP)) {
            ps.setLong(1, shopId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(new ProductHasShop(mapRsToProduct(rs), rs.getInt("count")));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ShopHasProduct> getShopsWithProduct(Long id) {
        List<ShopHasProduct> shops = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_SHOPS_WITH_PRODUCT)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    shops.add(new ShopHasProduct(mapRsToShop(rs), rs.getInt("count")));
                }
                return shops;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Long shopId, Long productId, Integer count) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setLong(1, shopId);
            ps.setLong(2, productId);
            ps.setInt(3, count);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Long shopId, Long productId, Integer count) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setInt(1, count);
            ps.setLong(2, shopId);
            ps.setLong(3, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long shopId, Long productId) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, shopId);
            ps.setLong(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
