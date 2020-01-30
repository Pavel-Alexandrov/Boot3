package crud.dao;

import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> userList = entityManager.createQuery("from User u").getResultList();
        entityManager.close();
        return userList;
    }

    @Override
    public void addUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public User deleteUser(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        entityManager.close();
        return user;
    }

    @Override
    public User getUserById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user = (User) entityManager.createQuery("FROM User U WHERE U.login = :lg").setParameter("lg", login).getSingleResult();
        entityManager.close();
        return user;
    }
}