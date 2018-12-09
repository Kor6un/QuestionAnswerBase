import dao.QuestionJDBCImpl;
import dao.ServiceJDBCImpl;
import dao.UserJDBCImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserJDBCImpl user = new UserJDBCImpl();
        QuestionJDBCImpl question = new QuestionJDBCImpl();
        user.addUser("Zmey");

//        new ServiceJDBCImpl().askQuestion("Kkk", "What are you doing?");
        new ServiceJDBCImpl().askQuestion("Zmey","What's up?");
//        new ServiceJDBCImpl().askQuestion("DraKoN","Where are you?");
//        new ServiceJDBCImpl().askQuestion("Kor6un","Kak dela?");
//
        new ServiceJDBCImpl().getAnswer("Zmey", "What's up?", "XZ1");
//        new ServiceJDBCImpl().getAnswer("DraKoN", "Where are you?", "I don't know.");
//        new ServiceJDBCImpl().getAnswer("Kkk", "What are you doing?", "Kill you)))");

//      new UserJDBCImpl().deleteUser("DraKoN");
//        new QuestionJDBCImpl().deleteQuestion("Kak dela?");

        System.out.println(new ServiceJDBCImpl().getStatistic());
    }
}
