package fu.de200046;

import fu.de200046.pojo.Employee;
import fu.de200046.pojo.Gender;
import fu.de200046.pojo.Project;
import fu.de200046.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            em.getTransaction().begin();

            Employee e1 = new Employee(
                    "Nguyen Van A",
                    "a@gmail.com",
                    Gender.MALE,
                    new BigDecimal("1500"),
                    LocalDate.of(2024, 1, 10)
            );

            Employee e2 = new Employee(
                    "Tran Thi B",
                    "b@gmail.com",
                    Gender.FEMALE,
                    new BigDecimal("1800"),
                    LocalDate.of(2024, 2, 15)
            );

            Employee e3 = new Employee(
                    "Le Van C",
                    "c@gmail.com",
                    Gender.MALE,
                    new BigDecimal("2000"),
                    LocalDate.of(2024, 3, 20)
            );

            Project p1 = new Project();
            p1.setProjectCode("P001");
            p1.setProjectName("EduFit");
            p1.setBudget(new BigDecimal("10000"));
            p1.setStartDate(LocalDate.of(2024, 1, 1));
            p1.setEndDate(null);

            Project p2 = new Project();
            p2.setProjectCode("P002");
            p2.setProjectName("E-Commerce");
            p2.setBudget(new BigDecimal("15000"));
            p2.setStartDate(LocalDate.of(2024, 2, 1));
            p2.setEndDate(null);

            e1.assignToProject(p1);
            e1.assignToProject(p2);

            e2.assignToProject(p2);

            e3.assignToProject(p1);

            em.persist(e1);
            em.persist(e2);
            em.persist(e3);

            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();

            System.out.println("===== PROJECTS OF EACH EMPLOYEE =====");

            System.out.println(e1.getFullName() + ":");
            for (Project p : e1.getProjects()) {
                System.out.println("- " + p.getProjectCode()
                        + " - " + p.getProjectName());
            }

            System.out.println(e2.getFullName() + ":");
            for (Project p : e2.getProjects()) {
                System.out.println("- " + p.getProjectCode()
                        + " - " + p.getProjectName());
            }

            System.out.println(e3.getFullName() + ":");
            for (Project p : e3.getProjects()) {
                System.out.println("- " + p.getProjectCode()
                        + " - " + p.getProjectName());
            }

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }
}