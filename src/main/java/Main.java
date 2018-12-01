import dao.UserJDBCImpl;
import dao.utils.Driver;

public class Main {
    public static void main(String[] args) {
        new UserJDBCImpl().addUser("Zmey");
//        Driver.connection();
//        System.out.println(true);
    }
}
