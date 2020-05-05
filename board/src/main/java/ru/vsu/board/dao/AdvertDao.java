package ru.vsu.board.dao;

import ru.vsu.board.models.Advert;

import java.util.List;
import java.util.Optional;

public interface AdvertDao {

    Optional<Advert> getById(Long id);
    List<Advert> getAll();
    Advert insert(String userEmail, Advert advert);
    void update(Advert advert);
    void deleteById(Long id);
    List<Advert> findByText(String text);
}
