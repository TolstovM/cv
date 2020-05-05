package ru.vsu.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.model.User;
import ru.vsu.persistence.repository.Repository;
import ru.vsu.persistence.util.Criteria;
import ru.vsu.services.exceptions.RegistrationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    public static final String REGISTER_EXCEPTION_MESSAGE = "Username %s in the use";
    public static final String LOGIN_BY_FIELD = "username";
    public static final int FIRST_ITEM_INDEX = 0;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private Repository<User> userRepository = new Repository<>(User.class);

    public void register(User user) throws RegistrationException, SQLException {
        List<User> users = getByUsername(user.getUsername());
        if (!users.isEmpty()) {
            throw new RegistrationException(String.format(REGISTER_EXCEPTION_MESSAGE, user.getUsername()));
        }
        userRepository.create(user);
    }

    public Optional<Long> login(String username, String password) {
        try {
            List<User> users = getByUsername(username);
            if (users.isEmpty()) {
                return Optional.empty();
            }

            User user = users.get(FIRST_ITEM_INDEX);
            if (user.getPassword().equals(password)) {
                return Optional.of(user.getId());
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error(e.getSQLState(), e);
            return Optional.empty();
        }
    }

    private List<User> getByUsername(String username) throws SQLException {
        return userRepository.findByCriteria(new Criteria(Criteria.EQUAL, LOGIN_BY_FIELD, username));
    }
}
