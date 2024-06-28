package ru.yandex.practicum.filmorate.modelTest;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserControllerTest {
    UserController userController;
    MockMvc mockMvc;
    User user;

    @BeforeEach
    public void beforeEachTest() {
        userController = new UserController();
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
        user = new User();
    }

    @Test // добавление фильма с корректными полями JUnit
    public void createUserTestAssert() {
        user.setEmail("iii@yandex.ru");
        user.setLogin("iii");
        user.setName("Ilya");
        user.setBirthday(LocalDate.of(1993, 8, 26));
        userController.create(user);
        Assertions.assertEquals(user.getId(), 1, "Пользователь не добавлен");
    }

    @Test // добавление фильма с корректными полями Mock
    public void createUserTestMock() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"iii@yandex.ru\","
                                + "\"login\":\"iii\","
                                + "\"name\":\"Ilya\","
                                + "\"birthday\":\"1993-08-26\"}"))
                .andExpect(status().isOk());
    }

    @Test // нельзя добавить пользователя с некорректным логином
    public void validBadLoginTest() {
        user.setEmail("iii@yandex.ru");
        user.setLogin("iii i");
        user.setName("Ilya");
        user.setBirthday(LocalDate.of(1993, 8, 26));
        Assertions.assertThrows(ValidationException.class, () -> userController.create(user),
                "Не работает проверка корректности логина");
    }

    @Test // нельзя передать некорректно логин
    public void validBadLoginAnTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"iii@yandex.ru\","
                                + "\"login\":\"\","
                                + "\"name\":\"iii\","
                                + "\"birthday\":\"1993-08-26\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test // обновление пользователя с корректными полями
    public void updateUserTest() {
        user.setEmail("iii@yandex.ru");
        user.setLogin("iii");
        user.setName("Ilya");
        user.setBirthday(LocalDate.of(1993, 8, 26));
        userController.create(user);

        User user1 = new User();
        user1.setEmail("iii@yandex.ru");
        user1.setLogin("iii");
        user1.setName("Oleg");
        user1.setId(1L);
        user1.setBirthday(LocalDate.of(1993, 8, 26));
        userController.update(user1);
        UserDTO userTest = userController.findAll().getFirst();
        Assertions.assertEquals(userTest.getName(), "Oleg", "Имя не обновлено");
    }

    @Test // нельзя передать некорректно имейл
    public void validBadEmailTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"iii\","
                                + "\"login\":\"iii\","
                                + "\"name\":\"Ilya\","
                                + "\"birthday\":\"1993-08-26\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test // нельзя передать некорректно дату рождения
    public void validBadBirthdayTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"iii@yandex.ru\","
                                + "\"login\":\"iii\","
                                + "\"name\":\"Ilya\","
                                + "\"birthday\":\"2024-08-26\"}"))
                .andExpect(status().isBadRequest());
    }
}