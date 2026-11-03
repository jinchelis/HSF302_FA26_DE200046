package com.example.data.service;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Pattern EMAIL =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student create(Student student) {
        normalize(student);
        validate(student);

        if (studentRepository.existsByEmailIgnoreCase(student.getEmail())) {
            throw new ValidationException("Email already exists");
        }

        student.setId(null);

        return studentRepository.save(student);
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional
    public Student update(Student student) {
        Student existing = findById(student.getId());

        normalize(student);
        validate(student);

        if (studentRepository.existsByEmailIgnoreCaseAndIdNot(
                student.getEmail(),
                student.getId())) {

            throw new ValidationException("Email already exists");
        }

        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setAge(student.getAge());

        return studentRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }

        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }

        return studentRepository.search(keyword.trim());
    }

    private void normalize(Student student) {
        if (student.getName() != null) {
            student.setName(student.getName().trim());
        }

        if (student.getEmail() != null) {
            student.setEmail(
                    student.getEmail().trim().toLowerCase()
            );
        }
    }

    private void validate(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new ValidationException("Name is required");
        }

        if (student.getName().length() > 100) {
            throw new ValidationException(
                    "Name must be at most 100 characters"
            );
        }

        if (student.getEmail() == null
                || !EMAIL.matcher(student.getEmail()).matches()) {
            throw new ValidationException("Invalid email");
        }

        if (student.getAge() == null
                || student.getAge() < 18
                || student.getAge() > 60) {
            throw new ValidationException(
                    "Age must be between 18 and 60"
            );
        }
    }
}