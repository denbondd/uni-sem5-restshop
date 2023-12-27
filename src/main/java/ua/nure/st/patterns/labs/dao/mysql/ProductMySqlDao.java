package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.controller.dto.AuthDto;
import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.entity.Product;
import ua.nure.st.patterns.labs.momento.ProductsHistory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMySqlDao implements ProductDao {

    private static final String SELECT_ALL = "SELECT * FROM product";
    private static final String SELECT_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String INSERT = "INSERT INTO product (name, price, brand_id, category_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE product SET name = ?, price = ?, brand_id = ?, category_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM product WHERE id = ?";
    private static final String SELECT_PRODUCTS_BY_NAME = "SELECT * FROM product WHERE name LIKE ?";
    private static final String SELECT_PRODUCTS_BY_BRAND = "SELECT * FROM product WHERE brand_id = ?";
    private static final String SELECT_PRODUCTS_BY_CATEGORY = "SELECT * FROM product WHERE category_id = ?";

    private final DataSource dataSource;
    private final ProductsHistory productsHistory;

    public ProductMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.productsHistory = new ProductsHistory(p -> updateAndLogToHistory(p, false));
    }

    public static Product mapRsToProduct(ResultSet rs) throws SQLException {
        return new Product.Builder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setPrice(rs.getLong("price"))
                .setBrandId(rs.getLong("brand_id"))
                .setCategoryId(rs.getLong("category_id"))
                .build();
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                products.add(mapRsToProduct(rs));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllByName(String name) {
        List<Product> products = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PRODUCTS_BY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRsToProduct(rs));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllByBrandId(Long brandId) {
        List<Product> products = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PRODUCTS_BY_BRAND)) {
            ps.setLong(1, brandId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRsToProduct(rs));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAllByCategoryId(Long categoryId) {
        List<Product> products = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_PRODUCTS_BY_CATEGORY)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRsToProduct(rs));
                }
                return products;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getById(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRsToProduct(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(String name, String description, Long price, Long brandId, Long categoryId, AuthDto user) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, name);
            ps.setLong(2, price);
            ps.setLong(3, brandId);
            ps.setLong(4, categoryId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateAndLogToHistory(Product product, boolean saveToHistory) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            ps.setLong(3, product.getBrandId());
            ps.setLong(4, product.getCategoryId());
            ps.setLong(5, product.getId());
            boolean isSuccess = ps.executeUpdate() > 0;
            if (isSuccess && saveToHistory) {
                productsHistory.push(product.getId(), product);
            }
            return isSuccess;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product, AuthDto auth) {
        return updateAndLogToHistory(product, true);
    }

    @Override
    public boolean undo(Long id, AuthDto user) {
        return productsHistory.undo(id);
    }

    @Override
    public boolean delete(Long id, AuthDto user) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
