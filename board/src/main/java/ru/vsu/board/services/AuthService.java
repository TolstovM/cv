package ru.vsu.board.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.vsu.board.dao.UserDao;
import ru.vsu.board.forms.LoginForm;
import ru.vsu.board.forms.SignUpForm;
import ru.vsu.board.models.User;
import ru.vsu.board.profiling.Profiling;

import javax.servlet.http.HttpServletRequest;

@Service
@Profiling
public class AuthService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;


    public AuthService() {
    }

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public void register(SignUpForm signUpForm) {
        User user = new User();
        user.setEmail(signUpForm.getEmail());
        user.setUsername(signUpForm.getUsername());
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        userDao.insert(user);
    }

    public void authenticate(LoginForm loginForm, HttpServletRequest req) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword());
        authReq.setDetails(new WebAuthenticationDetails(req));
        Authentication authenticate = authenticationManager.authenticate(authReq);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
