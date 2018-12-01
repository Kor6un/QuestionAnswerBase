import dao.QuestionJDBCImpl;
import dao.ServiceJDBCImpl;
import dao.UserJDBCImpl;
import dao.utils.Driver;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserJDBCImpl user = new UserJDBCImpl();
        QuestionJDBCImpl question = new QuestionJDBCImpl();
//        user.addUser("Zmey");
//        System.out.println(user.getUser("Zmey"));

        new ServiceJDBCImpl().askQuestion("Zmey", "How are you?");

       // System.out.println(question.getQuestion("What the fuck???"));


    }
}
