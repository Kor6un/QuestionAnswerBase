package dao;

import dao.entities.Answer;
import dao.utils.Driver;
import dao.utils.QuerySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerJDBCImpl extends AbstractJDBCImpl {
    public int addAnswer(String answer, int questionID, int userID, Connection connection) throws SQLException {
        int result = 0;
        if (getAnswer(userID, questionID, connection) == null) {
            try (PreparedStatement statement = connection.prepareStatement(QuerySQL.INSERT_ANSWER.getQuery())) {
                statement.setString(FIRST_ARGUMENT, answer);
                statement.setInt(SECOND_ARGUMENT, questionID);
                statement.setInt(THIRD_ARGUMENT, userID);
                result += statement.executeUpdate();
            }
        }
        return result;
    }

    private Answer getAnswer(int userId, int questionID, Connection connection) throws SQLException {
        Answer answer = null;
        ResultSet resultSet = null;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SELECT_ANSWER.getQuery())) {
           statement.setInt(FIRST_ARGUMENT, userId);
           statement.setInt(SECOND_ARGUMENT, questionID);

           resultSet = statement.executeQuery();
           if (resultSet.next()) {
               answer = new Answer(resultSet.getInt("id"),
                       resultSet.getString("answer"), userId, questionID);
           }

        } finally {
            Driver.closeResultSet(resultSet);
        }
        return answer;
    }

    public List<Answer> getBoundedLink(int id, String sql, Connection connection) throws SQLException {
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

    public void deleteAnswer(int answerID, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_ANSWER.getQuery())) {
            statement.setInt(FIRST_ARGUMENT, answerID);
            statement.execute();
        }
    }
}
