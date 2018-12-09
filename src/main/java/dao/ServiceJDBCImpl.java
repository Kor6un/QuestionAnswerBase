package dao;

import dao.utils.Driver;
import dao.utils.QuerySQL;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceJDBCImpl extends AbstractJDBCImpl {
    public void askQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);

            int userID = getEntityID(userName, QuerySQL.SELECT_USER_ID.getQuery(), connection);
            new QuestionJDBCImpl().addQuestion(question, userID);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            Driver.closeConnection(connection);
        }
    }

   /* private int getQuestionID(String question, Connection connection) throws SQLException {
        int questionID;
        if ((questionID = new QuestionJDBCImpl().
                getEntityID(question, QuerySQL.SELECT_QUESTION_ID.getQuery(), connection)) == -1) {
            throw new SQLException("unknown question");
        }
        return questionID;
    }
*/
    public void getAnswer(String userName, String question, String answer) throws SQLException {
        Connection connection = null;
        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);

            int userID = getEntityID(userName, QuerySQL.SELECT_USER_ID.getQuery(), connection);
            int questionID = getEntityID(question, QuerySQL.SELECT_QUESTION_ID.getQuery(), connection);
            new AnswerJDBCImpl().addAnswer(answer, questionID, userID, connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            Driver.closeConnection(connection);
        }
    }
    public String getStatistic() throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        ResultSet resultSet = null;
        try (Connection connection = Driver.connection();
                PreparedStatement statement = connection.prepareStatement(QuerySQL.SELECT_NAME_QUESTION_ANSWER.getQuery())) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString(FIRST_ARGUMENT)).append(" | ")
                        .append(resultSet.getString(SECOND_ARGUMENT)).append(" | ")
                        .append(resultSet.getString(THIRD_ARGUMENT)).append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
