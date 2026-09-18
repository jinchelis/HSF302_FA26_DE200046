package fu.de200046.dao;

import fu.de200046.pojo.Employee;
import fu.de200046.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EmployeeDAO {

    private final EntityManagerFactory emf = JPAUtil.getEMF();

    public void save(Employee e) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(e);

            em.getTransaction().commit();

        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw ex;

        } finally {
            em.close();
        }
    }
}