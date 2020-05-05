package ru.vsu.board.dao;

import ru.vsu.board.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getByIdWithRoles(Long id);
    Optional<User> getByEmailWithRoles(String email);
    List<User> getAll();
    User insert(User user);
    void update(User user);
}
