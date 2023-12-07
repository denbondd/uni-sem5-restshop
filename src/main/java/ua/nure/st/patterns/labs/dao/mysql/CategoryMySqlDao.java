package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.dao.CategoryDao;
import ua.nure.st.patterns.labs.entity.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryMySqlDao implements CategoryDao<Long> {
    private static final String SELECT_ALL = "SELECT * FROM category";
    private static final String SELECT_BY_ID = "SELECT * FROM category WHERE id = ?";
    private static final String INSERT = "INSERT INTO category (name) VALUES (?)";
    private static final String UPDATE = "UPDATE category SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM category WHERE id = ?";

    private final DataSource dataSource;

    public CategoryMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static Category<Long> mapRsToCategory(ResultSet rs) throws SQLException {
        return new Category.Builder<Long>()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .build();
    }

    private static PreparedStatement mapCategoryToRs(Category<Long> category, PreparedStatement ps) throws SQLException {
        ps.setLong(1, category.getId());
        ps.setString(2, category.getName());
        return ps;
    }

    @Override
    public List<Category<Long>> getAll() {
        List<Category<Long>> categorys = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categorys.add(mapRsToCategory(rs));
            }
            return categorys;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category<Long> getById(Long id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRsToCategory(rs);
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
    public boolean update(Category<Long> category) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            mapCategoryToRs(category, ps);
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
