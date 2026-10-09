
package com.example.ch6.config;

import com.example.ch6.entity.Major;
import com.example.ch6.entity.Student;
import com.example.ch6.repository.MajorRepository;
import com.example.ch6.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    public DataInitializer(StudentRepository studentRepository,
                           MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public void run(String... args) {

        // Tạo hoặc lấy chuyên ngành từ database
        Major cntt = getOrCreateMajor("CNTT", "Công nghệ thông tin");
        Major ktpm = getOrCreateMajor("KTPM", "Kỹ thuật phần mềm");
        Major httt = getOrCreateMajor("HTTT", "Hệ thống thông tin");
        Major attt = getOrCreateMajor("ATTT", "An toàn thông tin");
        Major mmt = getOrCreateMajor("MMT", "Mạng máy tính");

        // Chỉ seed sinh viên khi bảng chưa có dữ liệu
        if (studentRepository.count() == 0) {

            studentRepository.saveAll(List.of(
                    new Student(
                            "Nguyễn Văn An",
                            "an@example.com",
                            20,
                            cntt,
                            3.5
                    ),
                    new Student(
                            "Trần Thị Bình",
                            "binh@example.com",
                            21,
                            ktpm,
                            3.8
                    ),
                    new Student(
                            "Lê Văn Cường",
                            "cuong@example.com",
                            22,
                            httt,
                            3.2
                    ),
                    new Student(
                            "Phạm Thị Dung",
                            "dung@example.com",
                            19,
                            attt,
                            3.9
                    )
            ));

            System.out.println("Seeded 4 students successfully!");
        }

        System.out.println("Majors initialized successfully!");
    }

    private Major getOrCreateMajor(String code, String name) {
        return majorRepository.findByCode(code)
                .orElseGet(() -> {
                    Major major = new Major();
                    major.setCode(code);
                    major.setName(name);
                    return majorRepository.save(major);
                });
    }
}
