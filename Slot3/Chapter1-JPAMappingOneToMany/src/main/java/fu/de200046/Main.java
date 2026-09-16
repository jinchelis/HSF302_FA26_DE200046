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

        Department it = new Department("Marketing", "Ha Noi");

        Employee e1 = new Employee(
                "Nguyen Van A",
                "aa.nguyen@company.com",
                Gender.MALE,
                new BigDecimal("15000000"),
                LocalDate.of(2022, 1, 10)
        );

        Employee e2 = new Employee(
                "Tran Thi B",
                "bb.tran@company.com",
                Gender.FEMALE,
                new BigDecimal("18000000"),
                LocalDate.of(2021, 6, 1)
        );

        Employee e3 = new Employee(
                "Le Van C",
                "cc.le@company.com",
                Gender.OTHER,
                new BigDecimal("12000000"),
                LocalDate.of(2023, 3, 15)
        );


        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);


        departmentDAO.save(it);

        System.out.println("Department ID: " + it.getId());
        System.out.println("Employees: " + it.getEmployees().size());

        JPAUtil.close();
    }
}