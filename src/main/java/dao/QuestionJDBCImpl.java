package dao;

import dao.entities.Question;
import dao.utils.Driver;
import dao.utils.QuerySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class QuestionJDBCImpl extends AbstractJDBCImpl {
    public int addQuestion(String question, int userID) {
        int result;
        try (Connection connection = Driver.connection()) {
            result = 1;
            insertQuestion(question, userID, connection);
        } catch (SQLException e) {
            result = 0;
        }
        return result;
    }

    public Question getQuestion(String question) {
        Question result;
        try (Connection connection = Driver.connection()) {
            if ((result = selectQuestion(question, connection)) == null) {
                throw new SQLException("unknown question");
            }
        } catch (SQLException e) {
            result = null;
        }
        return result;
    }

    private Question selectQuestion(String question, Connection connection) throws SQLException {
        ResultSet resultSet = null;
        Question result = null;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SELECT_QUESTION.getQuery())){
            statement.setString(FIRST_ARGUMENT, question);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new Question(resultSet.getInt("id"), question, resultSet.getInt("user_id"));
            }
        } finally {
            Driver.closeResultSet(resultSet);
        }
        return result;
    }

    private void insertQuestion(String question, int userID, Connection connection) throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.INSERT_QUESTION.getQuery())){
            statement.setString(FIRST_ARGUMENT, question);
            statement.setInt(SECOND_ARGUMENT, userID);
            statement.execute();
        }
    }
}
