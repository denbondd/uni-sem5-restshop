package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.dao.BrandDao;
import ua.nure.st.patterns.labs.entity.Brand;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BrandMySqlDao implements BrandDao {
    private static final String SELECT_ALL = "SELECT * FROM brand";
    private static final String SELECT_BY_ID = "SELECT * FROM brand WHERE id = ?";
    private static final String INSERT = "INSERT INTO brand (name) VALUES (?)";
    private static final String UPDATE = "UPDATE brand SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM brand WHERE id = ?";

    private final DataSource dataSource;

    public BrandMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static Brand mapRsToBrand(ResultSet rs) throws SQLException {
        return new Brand.Builder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .build();
    }

    private static PreparedStatement mapBrandToRs(Brand brand, PreparedStatement ps) throws SQLException {
        ps.setLong(1, brand.getId());
        ps.setString(2, brand.getName());
        return ps;
    }

    @Override
    public List<Brand> getAll() {
        List<Brand> brands = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                brands.add(mapRsToBrand(rs));
            }
            return brands;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Brand getById(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRsToBrand(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(String name) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, name);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Brand brand) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            mapBrandToRs(brand, ps);
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
