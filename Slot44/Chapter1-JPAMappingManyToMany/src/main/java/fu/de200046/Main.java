package fu.de200046;

import fu.de200046.dao.DepartmentDAO;
import fu.de200046.pojo.Project;
import fu.de200046.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Project project = new Project(
                "P001",
                "EduFit",
                new BigDecimal("50000000"),
                LocalDate.of(2026, 9, 18),
                null
        );

        System.out.println(project.getProjectCode());
        System.out.println(project.getProjectName());
    }
}