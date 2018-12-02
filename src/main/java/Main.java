import dao.QuestionJDBCImpl;
import dao.ServiceJDBCImpl;
import dao.UserJDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserJDBCImpl user = new UserJDBCImpl();
        QuestionJDBCImpl question = new QuestionJDBCImpl();
        user.addUser("Kor6un");
//        System.out.println(user.getUser("Zmey"));

        new ServiceJDBCImpl().askQuestion("Zmey", "How are you?");
        new ServiceJDBCImpl().askQuestion("Zmey","What's up?");
        new ServiceJDBCImpl().askQuestion("DraKoN","Where are you?");
        new ServiceJDBCImpl().askQuestion("Kor6un","Kak dela?");

        new ServiceJDBCImpl().getAnswer("Zmey", "How are you?", "XZ1");
        new ServiceJDBCImpl().getAnswer("DraKoN", "Where are you?", "I don't know.");
        new ServiceJDBCImpl().getAnswer("Kor6un", "Where are you?", "Kak-to tak");

    }
}
