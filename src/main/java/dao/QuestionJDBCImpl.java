package dao;

import dao.entities.Question;
import dao.utils.Driver;

import java.sql.Connection;
import java.sql.SQLException;

import static dao.utils.QuerySQL.INSERT_QUESTION;
import static dao.utils.QuerySQL.SELECT_QUESTION_ID;

public class QuestionJDBCImpl extends AbstractJDBCImpl {
    public int addQuestion(String question) {
        int result;
        try (Connection connection = Driver.connection()) {
            result = 1;
            addEntity(question, INSERT_QUESTION.getQuery() , connection);
        } catch (SQLException e) {
            result = 0;
        }
        return result;
    }
    public Question getQuestion(String question) {
        Question result;
        try(Connection connection = Driver.connection()) {
            int id;
            if ((id = getEntityID(question, SELECT_QUESTION_ID.getQuery(), connection)) != -1) {
                result = new Question(id, question);
            } else {
                throw new SQLException("The user is not exist");
            }
        } catch (SQLException e) {
            result = null;
        }
        return result;
    }
}
