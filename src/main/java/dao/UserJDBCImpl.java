package dao;

import dao.entities.User;
import dao.utils.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import static dao.utils.QuerySQL.INSERT_USER;
import static dao.utils.QuerySQL.SELECT_USER_ID;

public class UserJDBCImpl extends AbstractJDBCImpl {
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
}
