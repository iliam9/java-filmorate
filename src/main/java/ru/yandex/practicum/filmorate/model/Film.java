package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.group.UpdateGroup;
import ru.yandex.practicum.filmorate.validation.StartRelease;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

/**
 * Film.
 */
@Data
@EqualsAndHashCode(exclude = {"id"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Film {
    @NotNull(groups = {UpdateGroup.class})
    Long id;
    @NotBlank(message = "Имя не может быть пустым")
    String name;
    @Size(max = 200, message = "Превышено количество символов")
    String description;
    @StartRelease
    LocalDate releaseDate;
    @Min(value = 0, message = "Продолжительность фильма не может быть отрицательным числом")
    Long duration;
    Set<Long> likes = new TreeSet<>();

    public void setLikes(Long id) {
        likes.add(id);
    }
}