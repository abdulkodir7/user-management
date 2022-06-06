package com.itransition.itransitiontask4.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Abdulqodir Ganiev 6/2/2022 8:35 AM
 */

@RestController
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    @PostMapping("/block-selected")
    public void blockUser(@RequestBody List<Long> users) {
        userService.blockSelectedUsers(users);
    }

    @PostMapping("/unblock-selected")
    public void unblockUser(@RequestBody List<Long> users) {
        userService.unblockSelectedUsers(users);
    }

    @PostMapping("/delete-selected")
    public void deleteUser(@RequestBody List<Long> users) {
        userService.deleteSelectedUsers(users);
    }
}
