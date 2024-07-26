package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.dto.UserDTO;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserStorage {

    Optional<List<UserDTO>> findAll();

    UserDTO create(User user);

    UserDTO update(User user);

    UserDTO addNewFriend(Long id, Long friendId);

    UserDTO deleteFriend(Long id, Long friendId);

    Optional<List<UserDTO>> getAllFriends(Long id);

    Optional<List<UserDTO>> getMutualFriends(Long id, Long otherId);

    Map<Long, User> getUsers();
}
