package ru.vsu.board.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vsu.board.forms.AdvertForm;
import ru.vsu.board.models.Advert;
import ru.vsu.board.profiling.Profiling;
import ru.vsu.board.security.UserPrincipal;
import ru.vsu.board.services.AdvertService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(AdvertController.URL)
@Profiling
public class AdvertController {

    public static final String ADVERTS_KEY = "adverts";
    public static final String USER_ID_KEY = "userId";
    public static final String ADVERT_KEY = "advertForm";
    public static final String AUTHENTICATION_IS_NECESSARY_MESSAGE = "Authentication is necessary";

    public static final String URL = "/user";
    public static final String CREATE_VIEW = "new-item";
    public static final String CREATE_PATH = "/new-item";
    public static final String SEARCH_VIEW = "search";
    public static final String SEARCH_PATH = "/search";
    public static final String DETAILS_VIEW = "details";
    public static final String DETAILS_PATH = "/items/details/{id}";
    public static final String REDIRECT_PREFIX = "redirect:";
    public static final String DELETE_PATH = "/items/delete/{id}";
    public static final String DETAILS_PATH_PRE = "/items/details/";


    private AdvertService advertService;

    public AdvertController() {
    }

    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @GetMapping(SEARCH_PATH)
    public String search(String text, Model model) {
        if (text == null || text.isEmpty()) {
            model.addAttribute(ADVERTS_KEY, advertService.getAll());
        } else {
            model.addAttribute(ADVERTS_KEY, advertService.search(text));
        }
        return SEARCH_VIEW;
    }

    @GetMapping(DETAILS_PATH)
    public String showDetails(@PathVariable Long id,
                              @AuthenticationPrincipal Authentication authentication,
                              HttpSession session,
                              Model model) {
        if (authentication != null) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            model.addAttribute(USER_ID_KEY, userPrincipal.getId());
        }
        Advert advert = advertService.getById(id);
        session.setAttribute(ADVERT_KEY, advert);
        model.addAttribute(ADVERT_KEY, advert);
        return DETAILS_VIEW;
    }

    @GetMapping(CREATE_PATH)
    public String create(Model model) {
        Advert advert = new Advert();
        model.addAttribute(ADVERT_KEY, advert);
        return CREATE_VIEW;
    }

    @PostMapping(CREATE_PATH)
    public String processCreate(@Valid AdvertForm advertForm,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return CREATE_VIEW;
        }
        Advert advert = advertService.create(authentication.getName(), advertForm);
        return REDIRECT_PREFIX + URL + DETAILS_PATH_PRE + advert.getId();
    }

    @GetMapping(DELETE_PATH)
    public String delete(@PathVariable Long id,
                         @AuthenticationPrincipal Authentication authentication,
                         HttpSession session) {
        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AUTHENTICATION_IS_NECESSARY_MESSAGE);
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Advert advert = (Advert) session.getAttribute(ADVERT_KEY);
        advertService.delete(userPrincipal.getId(), advert);
        return REDIRECT_PREFIX + URL + SEARCH_PATH;
    }

}
