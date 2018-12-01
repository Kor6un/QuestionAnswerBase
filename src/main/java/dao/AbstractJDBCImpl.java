package dao;

import dao.utils.Driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract class AbstractJDBCImpl {
    static final int FIRST_ARGUMENT = 1;
    static final int SECOND_ARGUMENT = 2;
    static final int THIRD_ARGUMENT = 3;

    int getEntityID(String value, String sql, Connection connection) throws SQLException {
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

    void addEntity(String value, String sql, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(FIRST_ARGUMENT, value);
            statement.execute();
        }
    }
}
