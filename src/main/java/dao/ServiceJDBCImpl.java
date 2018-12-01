package dao;

import dao.entities.Question;
import dao.utils.Driver;
import dao.utils.QuerySQL;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceJDBCImpl {
    public void askQuestion(String userName, String question) throws SQLException {
        Connection connection = null;
        try {
            connection = Driver.connection();
            connection.setAutoCommit(false);

            int userID;
            if ((userID = new UserJDBCImpl().getEntityID(userName, QuerySQL.SELECT_USER_ID.getQuery(), connection)) == -1){
                System.out.println(0);
                throw new SQLException("unknown user");
            }
            System.out.println(1);

            new QuestionJDBCImpl().addQuestion(question, userID);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("ex");
            connection.rollback();
        } finally {
            Driver.closeConnection(connection);
        }
    }
}
