package com.itransition.itransitiontask4.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.itransition.itransitiontask4.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/3/2022 7:41 PM
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found {} user ", email);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void blockSelectedUsers(List<Long> usersId) {
        for (Long userId : usersId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!user.getIsBlocked()) {
                user.setIsBlocked(true);
                userRepository.save(user);
            }

        }
    }

    @Transactional
    public void deleteSelectedUsers(List<Long> usersId) {
        userRepository.deleteAllByIdIn(usersId);
    }

    public void unblockSelectedUsers(List<Long> usersId) {
        for (Long userId : usersId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (user.getIsBlocked()) {
                user.setIsBlocked(false);
                userRepository.save(user);
            }
        }
    }

    public String registerUser(UserRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword()))
            return PASSWORD_NOT_MATCH;

        ;

        if (userRepository.findByEmail(request.getEmail()) != null)
            return EMAIL_EXISTS;

        User newUser = new User(
                request.getName(),
                request.getEmail(),
                getEncoder().encode(request.getPassword()),
                null,
                LocalDateTime.now(),
                false
        );

        userRepository.save(newUser);

        return SUCCESS_MESSAGE;
    }

    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
