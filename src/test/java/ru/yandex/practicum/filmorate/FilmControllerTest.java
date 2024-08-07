package ru.yandex.practicum.filmorate;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.dto.FilmDTO;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilmControllerTest {
    FilmController filmController;
    MockMvc mockMvc;
    Film film;

    @BeforeEach
    public void beforeEachTest() {
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
        mockMvc = MockMvcBuilders
                .standaloneSetup(filmController)
                .build();
        film = new Film();
    }

    @Test // добавление фильма с корректными полями Mock
    public void createFilmTestMock() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"iii iii \","
                                + "\"description\":\"iii iii\","
                                + "\"releaseDate\":\"2001-11-22\",\"duration\":121}"))
                .andExpect(status().isCreated());
    }

    @Test // нельзя создать фильм с неккоректной датой релиза
    public void validBadDateReleaseTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"iii iii iii\","
                                + "\"description\":\"iii iii iii iii\","
                                + "\"releaseDate\":\"1001-11-22\",\"duration\":121}"))
                .andExpect(status().isBadRequest());
    }

    @Test // обновление фильма с корректными полями
    public void updateFilmTest() {
        film.setName("iii iii iii iii iii iii");
        film.setDescription("iii iii iii iii");
        film.setDuration(121L);
        film.setReleaseDate(LocalDate.of(2001, 11, 22));
        filmController.create(film);

        Film film1 = new Film();
        film1.setName("iii iii iii iii iii iii iii");
        film1.setDescription("iii iii iii iii");
        film1.setDuration(174L);
        film1.setId(1L);
        film1.setReleaseDate(LocalDate.of(2002, 11, 14));
        filmController.update(film1);
        FilmDTO filmTest = filmController.findAll().get().getFirst();
        Assertions.assertEquals(filmTest.getDuration(), 174, "Фильм не обновлен");
        System.out.println(filmController.findAll());
    }

    @Test // нельзя обновить фильм с id которого нет
    public void updateWithBadIdTest() {
        film.setName("iii iii iii ooo iii iii");
        film.setDescription("iii iii ooo iii");
        film.setDuration(121L);
        film.setId(12L);
        film.setReleaseDate(LocalDate.of(2001, 11, 22));
        Assertions.assertThrows(NotFoundException.class, () -> filmController.update(film),
                "Не работает проверка корректности id");
    }


    @Test // нельзя присвоить полю продолжительность отрицательное значение
    public void validBadDurationTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"iii iii iii iii iii iii\","
                                + "\"description\":\"iii iii iii iii\","
                                + "\"releaseDate\":\"2001-11-22\",\"duration\":-1}"))
                .andExpect(status().isBadRequest());
    }

    @Test // нельзя присвоить полю имя пустое значение
    public void validBadNameTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\" \","
                                + "\"description\":\"iii iii iii iii\","
                                + "\"releaseDate\":\"2001-11-22\",\"duration\":121}"))
                .andExpect(status().isBadRequest());
    }

    @Test // нельзя присвоить полю описание строку размером больше 200 символов
    public void validBadDescriptionTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"iii iii iii iii iii iii\","
                                + "\"description\":\"iii iii iii iii. iii iii iii, iii iii iii " +
                                "iii iii iii iii iii iii-iii iii, iii iii" +
                                " iii, iii iii iii iii iii iii iii, iii iii. " +
                                "iii iii " +
                                "iii iii iii iii-iii iii, iii iii iii, iii iii iii iii " +
                                "iii iii iii iii iii iii iii, iii iii. iii iii iii" +
                                " iii iii iii, iii iii iii iii iii " +
                                "(iii iii iii).\","
                                + "\"releaseDate\":\"2001-11-22\",\"duration\":121}"))
                .andExpect(status().isBadRequest());
    }
}
