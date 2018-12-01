import dao.QuestionJDBCImpl;
import dao.UserJDBCImpl;
import dao.utils.Driver;

public class Main {
    public static void main(String[] args) {
        UserJDBCImpl user = new UserJDBCImpl();
        QuestionJDBCImpl question = new QuestionJDBCImpl();
        user.addUser("Zmey");
        System.out.println(user.getUser("Zmey"));

        question.addQuestion("What the fuck???");
        System.out.println(question.getQuestion("What the fuck???"));


    }
}
