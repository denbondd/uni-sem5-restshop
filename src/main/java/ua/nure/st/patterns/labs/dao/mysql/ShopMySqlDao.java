package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.dao.ShopDao;
import ua.nure.st.patterns.labs.entity.Shop;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopMySqlDao implements ShopDao<Long> {

    private static final String SELECT_ALL = "SELECT * FROM shop";
    private static final String SELECT_BY_ID = "SELECT * FROM shop WHERE id = ?";
    private static final String INSERT = "INSERT INTO shop (name, location) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE shop SET name = ?, location = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM shop WHERE id = ?";

    private final DataSource dataSource;

    public ShopMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static Shop<Long> mapRsToShop(ResultSet rs) throws SQLException {
        return new Shop.Builder<Long>()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setLocation(rs.getString("location"))
                .build();
    }

    private static PreparedStatement mapShopToRs(Shop<Long> shop, PreparedStatement ps) throws SQLException {
        ps.setLong(1, shop.getId());
        ps.setString(2, shop.getName());
        ps.setString(3, shop.getLocation());
        return ps;
    }

    @Override
    public List<Shop<Long>> getAll() {
        List<Shop<Long>> shops = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                shops.add(mapRsToShop(rs));
            }
            return shops;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Shop<Long>> getAllByName(String name) {
        List<Shop<Long>> shops = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                shops.add(mapRsToShop(rs));
            }
            return shops;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Shop<Long> getById(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRsToShop(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(String name, String location) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, name);
            ps.setString(2, location);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Shop<Long> shop) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            mapShopToRs(shop, ps);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
