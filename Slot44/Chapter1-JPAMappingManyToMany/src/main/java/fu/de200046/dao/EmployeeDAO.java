package fu.de200046.dao;

import fu.de200046.pojo.Employee;
import fu.de200046.pojo.Project;
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
        }}

        public void assignEmployeeToProject(Long employeeId, Long projectId) {
            EntityManager em = JPAUtil.getEMF().createEntityManager();

            try {
                em.getTransaction().begin();

                Employee employee = em.find(Employee.class, employeeId);
                Project project = em.find(Project.class, projectId);

                if (employee == null || project == null) {
                    throw new IllegalArgumentException("Employee or Project not found");
                }

                employee.assignToProject(project);

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

    public void deactivateEmployee(Long employeeId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            Employee employee = em.find(Employee.class, employeeId);

            if (employee == null) {
                throw new IllegalArgumentException("Employee not found");
            }

            employee.setActive(false);

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
    }
