package fu.de200046;

import fu.de200046.dao.DepartmentDAO;
import fu.de200046.pojo.Department;
import fu.de200046.pojo.Employee;
import fu.de200046.pojo.Gender;
import fu.de200046.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        departmentDAO.delete(3);

        System.out.println("Department deleted!");

        JPAUtil.close();
    }
}