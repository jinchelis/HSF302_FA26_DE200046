package com.example.data.service;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceImplTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void create_validStudent_savesSuccessfully() {
        Student student = new Student(
                "Nguyen Van An",
                "AN@FPT.EDU.VN",
                20
        );

        Student saved = studentService.create(student);

        assertNotNull(saved.getId());
        assertEquals("Nguyen Van An", saved.getName());
        assertEquals("an@fpt.edu.vn", saved.getEmail());
        assertEquals(20, saved.getAge());
    }

    @Test
    void create_trimsNameAndEmail() {
        Student student = new Student(
                "  Nguyen Van An  ",
                "  AN@FPT.EDU.VN  ",
                20
        );

        Student saved = studentService.create(student);

        assertEquals("Nguyen Van An", saved.getName());
        assertEquals("an@fpt.edu.vn", saved.getEmail());
    }

    @Test
    void create_blankName_throwsValidationException() {
        Student student = new Student(
                "   ",
                "an@fpt.edu.vn",
                20
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.create(student)
        );
    }

    @Test
    void create_invalidEmail_throwsValidationException() {
        Student student = new Student(
                "Nguyen Van An",
                "invalid-email",
                20
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.create(student)
        );
    }

    @Test
    void create_ageBelow18_throwsValidationException() {
        Student student = new Student(
                "Nguyen Van An",
                "an@fpt.edu.vn",
                17
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.create(student)
        );
    }

    @Test
    void create_ageAbove60_throwsValidationException() {
        Student student = new Student(
                "Nguyen Van An",
                "an@fpt.edu.vn",
                61
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.create(student)
        );
    }

    @Test
    void create_duplicateEmail_throwsValidationException() {
        studentService.create(
                new Student(
                        "Student 1",
                        "same@fpt.edu.vn",
                        20
                )
        );

        Student duplicate = new Student(
                "Student 2",
                "SAME@FPT.EDU.VN",
                21
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.create(duplicate)
        );
    }

    @Test
    void findById_existingStudent_returnsStudent() {
        Student saved = studentService.create(
                new Student(
                        "Nguyen Van An",
                        "an@fpt.edu.vn",
                        20
                )
        );

        Student found = studentService.findById(
                saved.getId()
        );

        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void findById_missingStudent_throwsStudentNotFoundException() {
        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.findById(999L)
        );
    }

    @Test
    void findAll_returnsStudentsOrderedById() {
        Student first = studentService.create(
                new Student(
                        "Student 1",
                        "one@fpt.edu.vn",
                        20
                )
        );

        Student second = studentService.create(
                new Student(
                        "Student 2",
                        "two@fpt.edu.vn",
                        21
                )
        );

        List<Student> students = studentService.findAll();

        assertEquals(2, students.size());
        assertEquals(first.getId(), students.get(0).getId());
        assertEquals(second.getId(), students.get(1).getId());
    }

    @Test
    void update_existingStudent_updatesSuccessfully() {
        Student saved = studentService.create(
                new Student(
                        "Old Name",
                        "old@fpt.edu.vn",
                        20
                )
        );

        Student edit = new Student(
                saved.getId(),
                "New Name",
                "NEW@FPT.EDU.VN",
                25
        );

        Student updated = studentService.update(edit);

        assertEquals("New Name", updated.getName());
        assertEquals("new@fpt.edu.vn", updated.getEmail());
        assertEquals(25, updated.getAge());
    }

    @Test
    void update_duplicateEmail_throwsValidationException() {
        Student first = studentService.create(
                new Student(
                        "Student 1",
                        "one@fpt.edu.vn",
                        20
                )
        );

        Student second = studentService.create(
                new Student(
                        "Student 2",
                        "two@fpt.edu.vn",
                        21
                )
        );

        Student edit = new Student(
                second.getId(),
                "Student 2",
                first.getEmail(),
                21
        );

        assertThrows(
                ValidationException.class,
                () -> studentService.update(edit)
        );
    }

    @Test
    void delete_existingStudent_removesStudent() {
        Student saved = studentService.create(
                new Student(
                        "Nguyen Van An",
                        "an@fpt.edu.vn",
                        20
                )
        );

        studentService.delete(saved.getId());

        assertFalse(
                studentRepository.existsById(saved.getId())
        );
    }

    @Test
    void delete_missingStudent_throwsStudentNotFoundException() {
        assertThrows(
                StudentNotFoundException.class,
                () -> studentService.delete(999L)
        );
    }

    @Test
    void search_blankKeyword_returnsAll() {
        studentService.create(
                new Student(
                        "Nguyen Van An",
                        "an@fpt.edu.vn",
                        20
                )
        );

        studentService.create(
                new Student(
                        "Tran Thi Binh",
                        "binh@gmail.com",
                        21
                )
        );

        assertEquals(2, studentService.search(" ").size());
    }

    @Test
    void search_keyword_returnsMatchingStudents() {
        studentService.create(
                new Student(
                        "Nguyen Van An",
                        "an@fpt.edu.vn",
                        20
                )
        );

        studentService.create(
                new Student(
                        "Tran Thi Binh",
                        "binh@gmail.com",
                        21
                )
        );

        assertEquals(1, studentService.search("Binh").size());
        assertEquals(1, studentService.search("fpt.edu").size());
    }
}