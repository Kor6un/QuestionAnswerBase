package dao;

import dao.entities.Answer;
import dao.entities.User;
import dao.utils.Driver;
import dao.utils.QuerySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

   /* private int getUserID(String userName, Connection connection) throws SQLException {
        int userID;
        if ((userID = new UserJDBCImpl().
                getEntityID(userName, QuerySQL.SELECT_USER_ID.getQuery(), connection)) == -1) {
            throw new SQLException("unknown user");
        }
        return userID;
    }*/

    public void deleteUser(String name) throws SQLException {
        UserJDBCImpl userJDBC = new UserJDBCImpl();
        QuestionJDBCImpl questionJDBC = new QuestionJDBCImpl();
        AnswerJDBCImpl answerJDBC = new AnswerJDBCImpl();
        Connection connection = null;

        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);

            int userID;
            if ((userID = getEntityID(name, QuerySQL.SELECT_USER_ID.getQuery(), connection)) == -1) {
                throw new SQLException("unknown user");
            }

            List<Answer> answers;
            int questionID;
            int answerID;

            if ((answers = answerJDBC.getBoundedLink(userID, QuerySQL.SELECT_USER_ANSWERS.getQuery(), connection)) != null) {
                for (Answer a: answers) {
                    questionID = a.getQuestionID();
                    answerID = a.getId();
                    answerJDBC.deleteAnswer(answerID, connection);
                    questionJDBC.deleteQuestionByID(questionID,connection);
                }
            }

            deleteUser(userID, connection);

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
        } finally {
            Driver.closeConnection(connection);
        }
    }

    private void deleteUser(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_USER.getQuery())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.execute();
        }
    }
}
