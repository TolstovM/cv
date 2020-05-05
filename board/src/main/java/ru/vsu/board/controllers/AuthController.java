package ru.vsu.board.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vsu.board.forms.LoginForm;
import ru.vsu.board.forms.SignUpForm;
import ru.vsu.board.profiling.Profiling;
import ru.vsu.board.services.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(AuthController.URL)
@Profiling
public class AuthController {

    public static final String LOGIN_FORM_KEY = "loginForm";
    public static final String SIGN_UP_FORM_KEY = "signUpForm";

    public static final String URL = "/auth";
    public static final String SIGNUP_PATH = "/signup";
    public static final String REDIRECT_PREFIX = "redirect:";
    public static final String LOGIN_PATH = "/login";
    public static final String LOGIN_VIEW = "login";
    public static final String SIGNUP_VIEW = "signup";

    private AuthService authService;

    public AuthController() {
    }

    @Autowired
    public AuthController(@Qualifier("authService") AuthService authService) {
        this.authService = authService;
    }


    @GetMapping(SIGNUP_PATH)
    public String signUpForm(Model model) {
        SignUpForm signUpForm = new SignUpForm();
        model.addAttribute(SIGN_UP_FORM_KEY, signUpForm);
        return SIGNUP_VIEW;
    }

    @PostMapping(SIGNUP_PATH)
    public String processSignUp(@Valid SignUpForm signUpForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_VIEW;
        }
        authService.register(signUpForm);
        return REDIRECT_PREFIX + URL + LOGIN_PATH;
    }

    @GetMapping(LOGIN_PATH)
    public String loginForm(Model model) {
        LoginForm loginForm = new LoginForm();
        model.addAttribute(LOGIN_FORM_KEY, loginForm);
        return LOGIN_VIEW;
    }

    @PostMapping(LOGIN_PATH)
    public String processLogin(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest req) {
        if (bindingResult.hasErrors()) {
            return LOGIN_VIEW;
        }
        authService.authenticate(loginForm, req);
        return REDIRECT_PREFIX + AdvertController.URL + AdvertController.SEARCH_PATH;
    }
}
