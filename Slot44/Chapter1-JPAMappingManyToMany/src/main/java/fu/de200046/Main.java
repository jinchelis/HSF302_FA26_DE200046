package fu.de200046;

import fu.de200046.dao.DepartmentDAO;
import fu.de200046.pojo.Employee;
import fu.de200046.pojo.Project;
import fu.de200046.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Employee e = new Employee();
        e.setEmail("test@gmail.com");

        Project p = new Project();
        p.setProjectCode("P001");

        e.assignToProject(p);

        System.out.println(e.getProjects().size());
        System.out.println(p.getEmployees().size());
    }
}