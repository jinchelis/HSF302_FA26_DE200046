package fu.de200046.dao;

import fu.de200046.pojo.Department;
import fu.de200046.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DepartmentDAO {

    private final EntityManagerFactory emf = JPAUtil.getEMF();

    public void save(Department d) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(d);

            em.getTransaction().commit();

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    public Department findByIdWithEmployees(int id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                            "SELECT d FROM Department d JOIN FETCH d.employees WHERE d.id = :id",
                            Department.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();

        } finally {
            em.close();
        }
    }

    public List<Department> findAll() {
        EntityManager em = emf.createEntityManager();

        try {
            List<Department> departments = em.createQuery(
                    "SELECT d FROM Department d",
                    Department.class
            ).getResultList();

            for (Department d : departments) {
                d.getEmployees().size();
            }

            return departments;

        } finally {
            em.close();
        }
    }
}
