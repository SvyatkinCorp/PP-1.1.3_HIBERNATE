package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String TABLENAME = "users";

    public UserDaoHibernateImpl() {
    }

    // Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует1
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS " + TABLENAME +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45)," +
                    " age INT NOT NULL)").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS " + TABLENAME).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Добавление User в таблицу
    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = Util.createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    //Удаление User из таблицы ( по id )
    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Получение всех User(ов) из таблицы
    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List users = null;
        try (Session session = Util.createSessionFactory().openSession()) {
            session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    // Очистка содержания таблицы
    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.createSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            for (User user : users) {
                session.delete(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

