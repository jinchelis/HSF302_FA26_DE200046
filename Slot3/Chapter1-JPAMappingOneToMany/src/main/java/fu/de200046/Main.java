package fu.de200046;

import fu.de200046.dao.DepartmentDAO;
import fu.de200046.pojo.Department;
import fu.de200046.pojo.Employee;
import fu.de200046.pojo.Gender;
import fu.de200046.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        Department department =
                departmentDAO.findByIdWithEmployees(1);

        System.out.println("Department: " + department.getName());

        for (Employee e : department.getEmployees()) {
            System.out.println(
                    e.getFullName() + " - " + e.getEmail()
            );
        }

        JPAUtil.close();
    }
}