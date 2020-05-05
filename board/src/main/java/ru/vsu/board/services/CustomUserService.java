package ru.vsu.board.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vsu.board.dao.UserDao;
import ru.vsu.board.models.User;
import ru.vsu.board.profiling.Profiling;
import ru.vsu.board.security.UserPrincipal;

@Service("userService")
@Profiling
public class CustomUserService implements UserDetailsService {

    public static final String USER_WITH_EMAIL_S_DOES_NOT_FOUNT_MESSAGE = "User with email: %s does not fount";
    private UserDao userDao;

    public CustomUserService() {
    }

    @Autowired
    public CustomUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getByEmailWithRoles(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_WITH_EMAIL_S_DOES_NOT_FOUNT_MESSAGE, email)));

        return UserPrincipal.create(user);
    }
}
