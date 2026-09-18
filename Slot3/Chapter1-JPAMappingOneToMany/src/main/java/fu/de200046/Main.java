package fu.de200046;

import fu.de200046.dao.DepartmentDAO;
import fu.de200046.util.JPAUtil;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        departmentDAO.delete(3);

        System.out.println("Department deleted!");

        JPAUtil.close();
    }
}