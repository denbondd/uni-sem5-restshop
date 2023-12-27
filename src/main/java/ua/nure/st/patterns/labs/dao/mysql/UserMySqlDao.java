package ua.nure.st.patterns.labs.dao.mysql;

import ua.nure.st.patterns.labs.dao.UserDao;
import ua.nure.st.patterns.labs.entity.User;

import javax.sql.DataSource;

public class UserMySqlDao implements UserDao {

    private final DataSource dataSource;

    public UserMySqlDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User login(String login, String password) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM user WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getLong("id"),
                        resultSet.getLong("role_id"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean signup(String login, String password, Integer roleId) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO user (login, password, role_id) VALUES (?, ?, ?)");
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setInt(3, roleId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
