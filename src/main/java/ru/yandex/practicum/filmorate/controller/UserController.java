package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    @PostMapping("/add")
    public User createUser(@Valid @RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Пользователь не указал имя, в качестве имени будет использован логин");
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь");
        return user;
    }

    @GetMapping("/list")
    public Collection<User> findAll() {
        log.info("Получен список всех пользователей");
        return users.values();
    }

    @PutMapping("/update")
    public User updateUser(@Valid @RequestBody User userToUpdate) {

        if (users.containsKey(userToUpdate.getId())) {

            User user = users.get(userToUpdate.getId());

            if (userToUpdate.getEmail() == null || userToUpdate.getEmail().isBlank()) {
                log.warn("Невозможно обновить пользователя, не задан адрес почты (или некорректный, пустой)");
                throw new ConditionsNotMetException("Задан некорректный адрес почты");
            }

            if (userToUpdate.getLogin() == null || userToUpdate.getLogin().isBlank()) {
                log.warn("Невозможно обновить пользователя, не задан логин");
                throw new ConditionsNotMetException("Логин не может быть пустым");
            }

            if (userToUpdate.getBirthday() == null || userToUpdate.getBirthday().isAfter(LocalDate.now())) {
                log.warn("Невозможно обновить пользователя, не указана дата рождения указана некорректная дата");
                throw new ConditionsNotMetException("Дата рождения не может быть в будущем или отсутствовать");
            }

            user.setName(userToUpdate.getName());
            user.setLogin(userToUpdate.getLogin());
            user.setEmail(userToUpdate.getEmail());
            user.setBirthday(userToUpdate.getBirthday());
            log.info("Данные пользователя обновлены");
            return userToUpdate;
        }
        throw new NotFoundException("Пользователь с id = " + userToUpdate.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
