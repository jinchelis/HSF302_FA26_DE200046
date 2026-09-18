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
        Employee e1 = new Employee();
        e1.setEmail("test@gmail.com");

        Employee e2 = new Employee();
        e2.setEmail("test@gmail.com");

        Set<Employee> employees = new HashSet<>();
        employees.add(e1);
        employees.add(e2);

        System.out.println(employees.size());
    }
}