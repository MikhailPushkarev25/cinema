package ru.job.cinema.service;

import org.springframework.stereotype.Service;
import ru.job.cinema.model.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {


    Map<Integer, Session> sessions = new ConcurrentHashMap<>();

    public SessionService() {
        sessions.put(1, new Session(1, "Бэтмэн"));
        sessions.put(2, new Session(2, "Человек паук"));
        sessions.put(3, new Session(3, "Маска"));
    }

    public List<Session> findAllSession() {
        return new ArrayList<>(sessions.values());
    }

    public Session findById(int id) {
        return sessions.get(id);
    }
}
