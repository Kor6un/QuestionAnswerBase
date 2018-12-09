package dao.hibernateImpl;

import dao.entities.hibernate.Users;
import dao.utils.HibernateDriver;

public class UserHibernateImpl {
    public Users getUser(int id) {
        return HibernateDriver.getInstance().getFactory().openSession().get(Users.class, id);
    }
}
