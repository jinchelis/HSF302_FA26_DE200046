package edu.fpt.hsf302.demologin.b3;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// TODO 1: "Database" lưu trong bộ nhớ
public final class UserStore {

    private static final Map<String, User> USERS =
            new HashMap<>();

    static {
        add(
                new User(
                        "Quản trị viên",
                        "admin@example.com",
                        "admin",
                        "Admin@123",
                        "Khác",
                        LocalDate.of(2000, 1, 1),
                        "Kỹ thuật phần mềm",
                        List.of()
                )
        );
    }

    private UserStore() {
    }

    public static boolean exists(String username) {

        return USERS.containsKey(
                username.trim().toLowerCase()
        );
    }

    public static void add(User user) {

        USERS.put(
                user.username(),
                user
        );
    }

    public static Optional<User> login(
            String username,
            String password
    ) {

        return Optional.ofNullable(
                        USERS.get(
                                username.trim().toLowerCase()
                        )
                )
                .filter(user ->
                        user.password().equals(password)
                );
    }
}