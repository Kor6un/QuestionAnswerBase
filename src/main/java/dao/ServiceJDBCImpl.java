package dao;

import dao.entities.Answer;
import dao.utils.Driver;
import dao.utils.QuerySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceJDBCImpl {
    public void askQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);

            int userID = getUserID(userName, connection);

            new QuestionJDBCImpl().addQuestion(question, userID);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            Driver.closeConnection(connection);
        }
    }

    private int getUserID(String userName, Connection connection) throws SQLException {
        int userID;
        if ((userID = new UserJDBCImpl().
                getEntityID(userName, QuerySQL.SELECT_USER_ID.getQuery(), connection)) == -1) {
            throw new SQLException("unknown user");
        }
        return userID;
    }

    private int getQuestionID(String question, Connection connection) throws SQLException {
        int questionID;
        if ((questionID = new QuestionJDBCImpl().
                getEntityID(question, QuerySQL.SELECT_QUESTION_ID.getQuery(), connection)) == -1) {
            throw new SQLException("unknown question");
        }
        return questionID;
    }

    public void getAnswer(String userName, String question, String answer) throws SQLException {
        Connection connection = null;
        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);


            int userID = getUserID(userName, connection);
            int questionID = getQuestionID(question, connection);
            new AnswerJDBCImpl().addAnswer(answer, questionID, userID, connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        Driver.closeConnection(connection);
    }
}
