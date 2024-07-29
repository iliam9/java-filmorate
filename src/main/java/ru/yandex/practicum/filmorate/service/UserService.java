package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User getById(int id);

    List<User> findAll();

    User create(User user);

    User update(User user);

    void addNewFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<User> getAllFriends(int id);

    List<User> getMutualFriends(int id, int otherId);
}
