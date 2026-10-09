package com.example.ch6.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class StudentForm {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 16, message = "Tuổi phải từ 16")
    private Integer age;

    @NotNull(message = "Vui lòng chọn chuyên ngành")
    private Long majorId;

    @NotNull(message = "GPA không được để trống")
    @DecimalMin(value = "0.0", message = "GPA phải từ 0")
    @DecimalMax(value = "4.0", message = "GPA không được vượt quá 4")
    private BigDecimal gpa;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Long getMajorId() { return majorId; }
    public void setMajorId(Long majorId) { this.majorId = majorId; }

    public BigDecimal getGpa() { return gpa; }
    public void setGpa(BigDecimal gpa) { this.gpa = gpa; }
}
