package edu.fpt.hsf302.demologin.b3;

import java.time.LocalDate;
import java.util.List;

// TODO 1: Model User
public record User(
        String fullName,
        String email,
        String username,
        String password,
        String gender,
        LocalDate dob,
        String major,
        List<String> hobbies
) {
}