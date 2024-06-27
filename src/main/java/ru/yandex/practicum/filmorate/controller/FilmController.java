package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();

    //Добавление фильма
    @PostMapping("/add")
    public Film createFilm(@Valid @RequestBody Film film) {

        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ConditionsNotMetException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм");
        return film;
    }

    @GetMapping("/list")
    public Collection<Film> findAll() {
        log.info("Получен список всех фильмов");
        return films.values();
    }

    @PutMapping("/update")
    public Film updateFilm(@Valid @RequestBody Film filmToUpdate) {

        if (films.containsKey(filmToUpdate.getId())) {

            Film film = films.get(filmToUpdate.getId());

            if (filmToUpdate.getDescription() == null || filmToUpdate.getDescription().length() > 200) {
                log.warn("Невозможно обновить фильм, описание более 200 символов или отсутствует");
                throw new ConditionsNotMetException("Максимальная длина описания — 200 символов");
            }

            if (filmToUpdate.getName() == null || filmToUpdate.getName().isBlank()) {
                log.warn("Невозможно обновить фильм, название пустое или отсутствует");
                throw new ConditionsNotMetException("Название фильма не может быть пустым");
            }

            if (filmToUpdate.getDuration() == null || filmToUpdate.getDuration() <= 0) {
                log.warn("Невозможно обновить фильм, указана продолжительность <= 0 или продолжительность не указана");
                throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
            }

            if (filmToUpdate.getReleaseDate() == null ||
                    filmToUpdate.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.warn("Невозможно обновить фильм, указана дата раньше 28 декабря 1895 года;");
                throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
            }
            film.setName(filmToUpdate.getName());
            film.setReleaseDate(filmToUpdate.getReleaseDate());
            film.setDescription(filmToUpdate.getDescription());
            film.setDuration(filmToUpdate.getDuration());
            log.info("Фильм обновлен");
            return film;
        }
        throw new NotFoundException("Фильм с id = " + filmToUpdate.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
