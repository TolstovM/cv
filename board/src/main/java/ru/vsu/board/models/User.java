package ru.vsu.board.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private List<Advert> adverts = new ArrayList<>();

    public User() {
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String username, String email, String password, List<Role> roles, List<Advert> adverts) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.adverts = adverts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public void addRole(Role role) {
        if(!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void addAdvert(Advert advert) {
        if (!this.adverts.contains(advert)) {
            this.adverts.add(advert);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
