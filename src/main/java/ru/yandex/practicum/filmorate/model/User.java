package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private long id;

    @NotEmpty(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Введен некорректный адрес электронной почты")
    private String email;

    @NotEmpty(message = "Логин не может быть пустым")
    @Pattern(regexp = "^\\S+$")
    private String login;

    private String name;
    @NotNull
    @PastOrPresent(message = "День рождения не может быть в будущем")
    private LocalDate birthday;
}
