package ru.job.cinema.service;

import org.springframework.stereotype.Service;
import ru.job.cinema.model.User;
import ru.job.cinema.persistence.UserDBStore;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findByEmailAndPhone(String email, String phone) {
        return store.findByEmailAndPhone(email, phone);
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public List<User> findAll() {
        return store.findAll();
    }
}
