package ru.yandex.practicum.filmorate.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ImportResource
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepositoryTest {
    UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("UserRepository_findById")
    void findByIdTest() {
        Optional<User> userOptional = userRepository.getById(1);
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                            assertThat(user).hasFieldOrPropertyWithValue("id", 1);
                            assertThat(user).hasFieldOrPropertyWithValue("login", "qqq");
                            assertThat(user).hasFieldOrPropertyWithValue("name", "qqq");
                            assertThat(user).hasFieldOrPropertyWithValue("email", "qqq@mail.ru");
                            assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                    LocalDate.of(1993, 3, 12));
                        }
                );
    }

    @Test
    @Order(2)
    @DisplayName("UserRepository_findAll")
    void findAllTest() {
        Optional<Collection<User>> userList = Optional.ofNullable(userRepository.findAll());
        assertThat(userList)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user).isNotEmpty();
                    assertThat(user).hasSize(4);
                    assertThat(user).element(0).hasFieldOrPropertyWithValue("id", 1);
                    assertThat(user).element(1).hasFieldOrPropertyWithValue("id", 2);
                    assertThat(user).element(2).hasFieldOrPropertyWithValue("id", 3);
                    assertThat(user).element(3).hasFieldOrPropertyWithValue("id", 4);
                });
    }

    @Test
    @Order(3)
    @DisplayName("UserRepository_create")
    void createTest() {
        User newUser = new User();
        newUser.setLogin("iii");
        newUser.setName("iii iii");
        newUser.setEmail("iii@yangex.ru");
        newUser.setBirthday(LocalDate.of(1993, 8, 26));

        Optional<User> userOptional = Optional.ofNullable(userRepository.create(newUser));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                            assertThat(user).hasFieldOrPropertyWithValue("id", 5);
                            assertThat(user).hasFieldOrPropertyWithValue("login", "iii");
                            assertThat(user).hasFieldOrPropertyWithValue("name", "iii iii");
                            assertThat(user).hasFieldOrPropertyWithValue("email",
                                    "iii@yangex.ru");
                            assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                    LocalDate.of(1993, 8, 26));
                        }
                );
    }

    @Test
    @Order(4)
    @DisplayName("UserRepository_update")
    void updateTest() {
        User newUser = new User();
        newUser.setId(5);
        newUser.setLogin("ilyam");
        newUser.setName("ilya manenok");
        newUser.setEmail("ilyya@yangex.ru");
        newUser.setBirthday(LocalDate.of(1993, 8, 26));

        Optional<User> userOptional = Optional.ofNullable(userRepository.update(newUser));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                            assertThat(user).hasFieldOrPropertyWithValue("id", 5);
                            assertThat(user).hasFieldOrPropertyWithValue("login", "ilyam");
                            assertThat(user).hasFieldOrPropertyWithValue("name", "ilya manenok");
                            assertThat(user).hasFieldOrPropertyWithValue("email",
                                    "ilyya@yangex.ru");
                            assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                    LocalDate.of(1993, 8, 26));
                        }
                );
    }

    @Test
    @Order(5)
    @DisplayName("UserRepository_getAllFriends")
    void getAllFriendsTest() {
        Optional<List<User>> usersOptional = Optional.ofNullable(userRepository.getAllFriends(1));
        assertThat(usersOptional)
                .isPresent()
                .hasValueSatisfying(users -> {
                            assertThat(users).isNotEmpty();
                            assertThat(users).hasSize(2);
                            assertThat(users).first().hasFieldOrPropertyWithValue("id", 2);
                            assertThat(users).element(1).hasFieldOrPropertyWithValue("id", 3);
                        }
                );
    }

    @Test
    @Order(6)
    @DisplayName("UserRepository_getMutualFriends")
    void getMutualFriendsTest() {
        Optional<List<User>> commonFriendsOptional = Optional
                .ofNullable(userRepository.getMutualFriends(2, 3));
        assertThat(commonFriendsOptional)
                .isPresent()
                .hasValueSatisfying(users -> {
                            assertThat(users).isNotEmpty();
                            assertThat(users).hasSize(2);
                            assertThat(users).element(0).hasFieldOrPropertyWithValue("id", 1);
                            assertThat(users).element(1).hasFieldOrPropertyWithValue("id", 4);
                        }
                );
    }
}
