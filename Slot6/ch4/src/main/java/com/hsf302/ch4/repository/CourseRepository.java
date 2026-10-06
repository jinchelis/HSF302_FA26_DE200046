package com.hsf302.ch4.repository;

import com.hsf302.ch4.dto.CourseEnrollmentCount;
import com.hsf302.ch4.dto.CourseStatDTO;
import com.hsf302.ch4.pojo.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // bổ sung dần từ TODO 7
    Optional<Course> findByCode(String code);                     // đã thêm ở TODO 7

    List<Course> findBySemesterOrderByCodeAsc(String semester);

    long countBySemester(String semester);

    List<Course> findByStudents_StudentCodeOrderByCodeAsc(String studentCode);

    List<Course> findByStudents_Department_CodeOrderByCodeAsc(String deptCode);          // có thể TRÙNG

    List<Course> findDistinctByStudents_Department_CodeOrderByCodeAsc(String deptCode);  // loại trùng

    List<Course> findByStudentsIsEmpty();

    @Query("SELECT new com.hsf302.ch4.dto.CourseStatDTO(c.code, c.name, c.capacity, COUNT(s), AVG(s.gpa)) " + "FROM Course c LEFT JOIN c.students s " + "GROUP BY c.code, c.name, c.capacity ORDER BY c.code")
    List<CourseStatDTO> getCourseStats();

    @Query("SELECT c FROM Course c WHERE SIZE(c.students) >= c.capacity ORDER BY c.code")
    List<Course> findFullCourses();

    // CourseRepository
    @EntityGraph(attributePaths = "students")
    Optional<Course> findWithStudentsByCode(String code);

    @Query(value = "SELECT TOP (:n) c.code AS code, c.name AS name, COUNT(sc.student_id) AS enrolled " + "FROM courses c LEFT JOIN student_courses sc ON sc.course_id = c.id " + "GROUP BY c.code, c.name " + "ORDER BY enrolled DESC, c.code", nativeQuery = true)
    List<CourseEnrollmentCount> findTopEnrolledNative(@Param("n") int n);

    Optional<Course> findByNameIgnoreCase(String keyword);

    @Query("SELECT c FROM Course c WHERE c.credits BETWEEN :min AND :max ORDER BY c.code")
    List<Course> findByCreditBetweenMinAndMax(@Param("min") Double min, @Param("max") Double max);

    List<Course> findByNameIgnoreCaseContaining(String keyword);

    @Query("SELECT c FROM Course c WHERE c.credits > 3 ORDER BY c.code")
    List<Course> findByCreditsGreaterThanThree();
}
