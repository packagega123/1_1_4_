package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        /*Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();*/

        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";

        /*Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();*/
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        /*Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();*/

        String sql = "DROP TABLE IF EXISTS users";

        /*Query query = session.createSQLQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close();*/
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createSQLQuery(sql).addEntity(User.class);

            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        /*Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("from User", User.class).list();
        session.close();
        return users;*/
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        /*Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();*/

        String sql = "DELETE FROM users";

        /*Query query = session.createNativeQuery(sql).addEntity(User.class);

        transaction.commit();
        session.close()*/;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createNativeQuery(sql).addEntity(User.class);

            transaction.commit();
        }
    }
}
