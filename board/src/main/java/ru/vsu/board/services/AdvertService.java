package ru.vsu.board.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.vsu.board.dao.AdvertDao;
import ru.vsu.board.forms.AdvertForm;
import ru.vsu.board.models.Advert;
import ru.vsu.board.profiling.Profiling;

import java.util.List;

@Service
@Profiling
public class AdvertService {

    public static final String ADVERT_DOES_NOT_FOUND_MESSAGE = "Advert does not found";
    public static final String DELETE_ADVERT_ACCESS_EXCEPTION_MESSAGE = "It is not your advert.";
    private AdvertDao advertDao;

    public AdvertService() {
    }

    @Autowired
    public AdvertService(AdvertDao advertDao) {
        this.advertDao = advertDao;
    }

    public Advert getById(Long id) {
        return advertDao.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ADVERT_DOES_NOT_FOUND_MESSAGE));
    }

    public List<Advert> getAll() {
        return advertDao.getAll();
    }

    public List<Advert> search(String text) {
        return advertDao.findByText(text);
    }

    public Advert create(String userEmail, AdvertForm advertForm) {
        Advert advert = new Advert();
        advert.setName(advertForm.getName());
        advert.setDescription(advertForm.getDescription());
        advert.setPrice(advertForm.getPrice());
        return advertDao.insert(userEmail, advert);
    }

    public void delete(Long userId, Advert advert) {
        if (!userId.equals(advert.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, DELETE_ADVERT_ACCESS_EXCEPTION_MESSAGE);
        }
        advertDao.deleteById(advert.getId());
    }
}
