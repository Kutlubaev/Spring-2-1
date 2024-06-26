package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u JOIN fetch u.usercar",User.class);
      return query.getResultList();
   }

   public User getUserByCar(String model, int series) {
      String hql = "SELECT u FROM User u JOIN FETCH u.usercar WHERE u.usercar.model = :model AND u.usercar.series = :series";
      Query query = sessionFactory.getCurrentSession().createQuery(hql);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return (User) query.getSingleResult();
   }

}
