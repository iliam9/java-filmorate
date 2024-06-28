package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @NotNull
    long id;

    @Email(message = "Введен некорректный адрес электронной почты")
    String email;

    @NotBlank(message = "Поле не может быть пустым")
    @Pattern(regexp = "^\\S+$")
    String login;

    String name;

    @Past(message = "День рождения не может быть позднее настоящего времени")
    LocalDate birthday;
}
