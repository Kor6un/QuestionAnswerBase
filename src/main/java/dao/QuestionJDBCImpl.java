package dao;

import dao.entities.Answer;
import dao.entities.Question;
import dao.utils.Driver;
import dao.utils.QuerySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public void deleteQuestion(String question) throws SQLException {
        Connection connection = null;

        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);

            int questionID;
            if ((questionID = getEntityID(question, QuerySQL.SELECT_QUESTION.getQuery(), connection)) == -1) {
                throw new SQLException("the question not found");
            }

            List<Answer> answers;
            if ((answers = new AnswerJDBCImpl().getBoundedLink(questionID, QuerySQL.SELECT_QUESTION_ANSWER.getQuery(), connection)) != null) {
                AnswerJDBCImpl answerJDBC = new AnswerJDBCImpl();
                for (Answer a: answers) {
                    answerJDBC.deleteAnswer(a.getId(), connection);
                }
            }

            deleteQuestionByID(questionID, connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            Driver.closeConnection(connection);
        }
    }

    public void deleteQuestionByID(int questionID, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(QuerySQL.SELECT_QUESTION_ANSWER.getQuery());
        statement.setInt(FIRST_ARGUMENT, questionID);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            statement = connection.prepareStatement(QuerySQL.DELETE_QUESTION.getQuery());
            statement.setInt(FIRST_ARGUMENT, questionID);
            statement.execute();
        }
    }

    private Question selectQuestion(String question, Connection connection) throws SQLException {
        ResultSet resultSet = null;
        Question result = null;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SELECT_QUESTION.getQuery())){
            statement.setString(FIRST_ARGUMENT, question);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new Question(resultSet.getInt("id"), question);
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
