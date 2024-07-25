package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.group.UpdateGroup;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Optional<List<UserDTO>> findAll() {
        log.info("All users received");
        return userService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@Valid @RequestBody User user) {
        log.info("User created");
        return userService.create(user);
    }

    @PutMapping
    public UserDTO update(@Validated(UpdateGroup.class) @RequestBody User user) {
        log.info("User updated");
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public UserDTO addNewFriend(@PathVariable @Positive Long id, @PathVariable @Positive Long friendId) {
        log.info("New friend added");
        return userService.addNewFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public UserDTO deleteFriend(@PathVariable @Positive Long id, @PathVariable @Positive Long friendId) {
        log.info("Friend deleted");
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public Optional<List<UserDTO>> getAllFriends(@PathVariable @Positive Long id) {
        log.info("All friends received");
        return userService.getAllFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Optional<List<UserDTO>> getMutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Mutual friends received");
        return userService.getMutualFriends(id, otherId);
    }
}