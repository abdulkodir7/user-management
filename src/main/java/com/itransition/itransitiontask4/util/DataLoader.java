package com.itransition.itransitiontask4.util;


import com.itransition.itransitiontask4.user.User;
import com.itransition.itransitiontask4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    String initMode;


    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            User abdulqodir = new User(
                    "Abdulkodir",
                    "a@gmail.com",
                    passwordEncoder.encode("1234"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false
            );
            User asadbek = new User(
                    "Sasha",
                    "s@gmail.com",
                    passwordEncoder.encode("1234"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false
            );
            User sardor = new User(
                    "John",
                    "j@gmail.com",
                    passwordEncoder.encode("1234"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    false
            );
            User nurbek = new User(
                    "Test",
                    "t@gmail.com",
                    passwordEncoder.encode("1234"),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    true
            );

            userRepository.save(abdulqodir);
            userRepository.save(asadbek);
            userRepository.save(nurbek);
            userRepository.save(sardor);
        }
    }
}
