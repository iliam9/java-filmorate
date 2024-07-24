package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.group.UpdateGroup;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
@Validated
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private FilmService filmService;

    @GetMapping
    public Optional<List<FilmDTO>> findAll() {
        log.info("Film list received");
        return filmService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmDTO create(@Valid @RequestBody Film film) {
        log.info("Film created");
        return filmService.create(film);
    }

    @PutMapping
    public FilmDTO update(@Validated(UpdateGroup.class) @RequestBody Film film) {
        log.info("Film updated");
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmDTO putLike(@PathVariable @Positive Long id, @PathVariable @Positive Long userId) {
        log.info("Liked");
        return filmService.putLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmDTO deleteLike(@PathVariable @Positive Long id, @PathVariable @Positive Long userId) {
        log.info("Like removed");
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public Optional<Collection<FilmDTO>> getBestFilm(@RequestParam(defaultValue = "10") @Positive Long count) {
        log.info("Best film received");
        return filmService.getBestFilm(count);
    }
}
