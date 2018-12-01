package dao;

import dao.entities.User;
import dao.utils.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.entities.QuerySQL.INSERT_USER;
import static dao.entities.QuerySQL.SELECT_USER_ID;

public class UserJDBCImpl {
    private static final int FIRST_ARGUMENT = 1;
    private static final int SECOND_ARGUMENT = 2;
    private static final int THIRD_ARGUMENT = 3;

    public int addUser(String name) {
        int result;
        try (Connection connection = Driver.connection()) {
            result = 1;
            addEntity(name, INSERT_USER.getQuery() , connection);
        } catch (SQLException e) {
            result = 0;
        }
        return result;
    }

    public User getUser(String name) {
        User user;
        try(Connection connection = Driver.connection()) {
            int id;
            if ((id = getEntityID(name, SELECT_USER_ID.getQuery(), connection)) != -1) {
                user = new User(id, name);
            } else {
                throw new SQLException("The user is not exist");
            }
        } catch (SQLException e) {
            user = null;
        }
        return user;
    }

    private int getEntityID(String value, String sql, Connection connection) throws SQLException {
        ResultSet resultSet = null;
        int result = -1;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(FIRST_ARGUMENT, value);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
               result =  resultSet.getInt("id");
            }
        } finally {
            Driver.closeResultSet(resultSet);
        }
        return result;
    }

    private void addEntity(String value, String sql, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(FIRST_ARGUMENT, value);
            statement.execute();
        }
    }

}
