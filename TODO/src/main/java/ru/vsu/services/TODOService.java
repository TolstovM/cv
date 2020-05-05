package ru.vsu.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.model.TODOItem;
import ru.vsu.model.User;
import ru.vsu.persistence.repository.Repository;
import ru.vsu.persistence.util.Criteria;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TODOService {

    public static final String USER_ID_FIELD = "userId";
    private static final Logger logger = LoggerFactory.getLogger(TODOService.class);
    private Repository<TODOItem> todoItemRepository = new Repository<TODOItem>(TODOItem.class);

    public Optional<TODOItem> getById(TODOItem item) {
        try {
            return todoItemRepository.getById(item.getId());
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            return Optional.empty();
        }
    }

    public List<TODOItem> getAll() {
        try {
            return todoItemRepository.getAll();
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            return new ArrayList<>();
        }
    }

    public List<TODOItem> getAllForUser(User user) {
        try {
            return todoItemRepository.findByCriteria(new Criteria(Criteria.EQUAL, USER_ID_FIELD, user.getId()));
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            return new ArrayList<>();
        }
    }

    public boolean create(TODOItem item) {
        try {
            return todoItemRepository.create(item);
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            return false;
        }
    }

    public boolean update(TODOItem item) {
        try {
            return todoItemRepository.update(item);
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            return false;
        }
    }

    public void delete(TODOItem item) {
        try {
            todoItemRepository.deleteById(item.getId());
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
        }
    }
}
