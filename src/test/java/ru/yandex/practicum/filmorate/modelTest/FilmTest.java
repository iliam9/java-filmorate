package ru.yandex.practicum.filmorate.modelTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

class FilmTest {

    private final Film film = Film.builder()
            .name("film")
            .description("description")
            .releaseDate(LocalDate.parse("1895-12-28").plusDays(1))
            .duration(100)
            .build();
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void shouldCreateFilm() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldCreateFilmWithFirstFilmDate() {
        Film filmWithFirstFilmDate = Film
                .builder()
                .name("film")
                .description("description")
                .duration(100)
                .releaseDate(LocalDate.parse("1895-12-28"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(filmWithFirstFilmDate);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotCreateFilmIfNameIsEmpty() {
        String[] names = {"", " ", "  ", null};

        Arrays.stream(names).forEach(name -> {
            Film filmWithIncorrectName = Film
                    .builder()
                    .name(name)
                    .build();

            Set<ConstraintViolation<Film>> violations = validator.validate(filmWithIncorrectName);

            Assertions.assertFalse(violations.isEmpty());
        });
    }

    @Test
    void shouldNotCreateFilmIfDescriptionTooLong() {
        Film filmWithIncorrectDescription = Film
                .builder()
                .name("film")
                .description("a".repeat(200 + 1))
                .duration(100)
                .releaseDate(LocalDate.parse("1895-12-28"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(filmWithIncorrectDescription);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void shouldNotCreateFilmIfReleaseDateIsWrong() {
        Film filmWithIncorrectReleaseDate = Film
                .builder()
                .name("film")
                .description("description")
                .duration(100)
                .releaseDate(LocalDate.of(1895, 12, 26))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(filmWithIncorrectReleaseDate);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void shouldNotCreateFilmIfDurationIsWrong() {
        Film filmWithIncorrectDuration = Film
                .builder()
                .duration(-100)
                .name("film")
                .description("description")
                .releaseDate(LocalDate.parse("1895-12-28"))
                .build();

        Set<ConstraintViolation<Film>> violations = validator.validate(filmWithIncorrectDuration);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertEquals(1, violations.size());
    }
}