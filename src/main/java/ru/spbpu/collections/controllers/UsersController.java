package ru.spbpu.collections.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.spbpu.collections.model.dto.UserDto;
import ru.spbpu.collections.service.UserService;

import static ru.spbpu.collections.model.RoleValues.ROLE_ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.base-prefix}" + "/users")
public class UsersController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @PostMapping("/me/friends/{friendId}")
    public ResponseEntity<UserDto> addFriend(@PathVariable("friendId") final Long friendId) {
        return ResponseEntity.ok(userService.addFriend(friendId));
    }

    @DeleteMapping("/me/friends/{friendId}")
    public ResponseEntity<UserDto> deleteFriend(@PathVariable("friendId") final Long friendId) {
        return ResponseEntity.ok(userService.deleteFriend(friendId));
    }

    @GetMapping("/{id}")
    @RolesAllowed(ROLE_ADMIN)
    public ResponseEntity<UserDto> findById(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
