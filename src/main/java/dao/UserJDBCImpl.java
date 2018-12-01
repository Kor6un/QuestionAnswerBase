package dao;

import dao.utils.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static dao.entities.QuerySQL.INSERT_USER;

public class UserJDBCImpl {
    private static final int FIRST_ARGUMENT = 1;
    private static final int SECOND_ARGUMENT = 2;
    private static final int THIRD_ARGUMENT = 3;

    public void addUser(String name) {
        try (Connection connection = Driver.connection()) {
            addEntity(name, INSERT_USER.getQuery() , connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEntity(String value, String sql, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(FIRST_ARGUMENT, value);
            statement.execute();
        }
    }

}
