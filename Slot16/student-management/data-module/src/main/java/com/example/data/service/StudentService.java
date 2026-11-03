package com.example.data.service;

import com.example.data.pojo.Student;

import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student findById(Long id);

    List<Student> findAll();

    Student update(Student student);

    void delete(Long id);

    List<Student> search(String keyword);
}