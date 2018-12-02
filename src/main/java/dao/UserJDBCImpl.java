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

    public void deleteUser(String name) throws SQLException {
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

            if ((answers = getBoundedLink(userID, QuerySQL.SELECT_USER_ANSWERS.getQuery(), connection)) != null) {
                for (Answer a: answers) {
                    questionID = a.getQuestionID();
                    answerID = a.getId();
                    deleteAnswer(answerID, connection);
                    deleteQuestion(questionID,connection);
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

    private void deleteQuestion(int questionID, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_QUESTION.getQuery())) {
            statement.setInt(FIRST_ARGUMENT, questionID);
            statement.execute();
        }
    }

    private void deleteAnswer(int answerID, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_ANSWER.getQuery())) {
            statement.setInt(FIRST_ARGUMENT, answerID);
            statement.execute();
        }
    }

    private List<Answer> getBoundedLink(int id, String sql, Connection connection) throws SQLException {
        List<Answer> answers;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(FIRST_ARGUMENT, id);

            resultSet = statement.executeQuery();
            answers = new ArrayList<>();
            while (resultSet.next()) {
                answers.add(new Answer(resultSet.getInt("id"), resultSet.getString("answer"),
                        resultSet.getInt("question_id"), resultSet.getInt("user_id")));
            }
            return answers.size() > 0 ? answers : null;
        } finally {
            Driver.closeResultSet(resultSet);
        }
    }

    private void deleteUser(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_USER.getQuery())) {
            statement.setInt(FIRST_ARGUMENT, userId);
            statement.execute();
        }
    }
}
