import dao.UserJDBCImpl;
import dao.utils.Driver;

public class Main {
    public static void main(String[] args) {
        UserJDBCImpl user = new UserJDBCImpl();
        user.addUser("Zmey");
        System.out.println(user.getUser("Zmey") );

//        Driver.connection();
//        System.out.println(true);
    }
}
