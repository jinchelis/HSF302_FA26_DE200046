package fu.de200046;

import fu.de200046.dao.DepartmentDAO;
import fu.de200046.pojo.Department;
import fu.de200046.pojo.Employee;
import fu.de200046.util.JPAUtil;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        List<Department> departments =
                departmentDAO.findAllWithEmployees();

        for (Department d : departments) {
            System.out.println("Department: " + d.getName());

            for (Employee e : d.getEmployees()) {
                System.out.println("  Employee: " + e.getFullName());
            }
        }

        JPAUtil.close();
    }
}