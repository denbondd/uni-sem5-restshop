package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.entity.Product;

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

    public ProductMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
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

    private static PreparedStatement mapProductToRs(Product product, PreparedStatement ps) throws SQLException {
        ps.setLong(1, product.getId());
        ps.setString(2, product.getName());
        ps.setLong(3, product.getPrice());
        ps.setLong(4, product.getBrandId());
        ps.setLong(5, product.getCategoryId());
        return ps;
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
    public boolean save(String name, String description, Long price, Long brandId, Long categoryId) {
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

    @Override
    public boolean update(Product product) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            mapProductToRs(product, ps);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
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
