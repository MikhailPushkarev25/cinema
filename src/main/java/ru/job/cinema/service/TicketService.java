package ru.job.cinema.service;

import org.springframework.stereotype.Service;
import ru.job.cinema.model.Ticket;
import ru.job.cinema.persistence.TicketDBStore;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketDBStore store;
    private final SessionService sessionService;

    public TicketService(TicketDBStore store, SessionService sessionService) {
        this.store = store;
        this.sessionService = sessionService;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = store.findAll();
        tickets.forEach(
                ticket -> ticket.setSession_id(sessionService.findById(ticket.getSession_id().getId()))
        );
        return tickets;
    }

    public Optional<Ticket> add(Ticket ticket) {
            return Optional.ofNullable(store.add(ticket));
    }

    public Ticket findById(int id) {
        Ticket ticket = store.findById(id);
        ticket.setSession_id(sessionService.findById(ticket.getSession_id().getId()));
        return ticket;
    }

    public void update(Ticket ticket) {
        store.update(ticket);
    }

    public Ticket delete(Ticket ticket) {
        return store.delete(ticket);
    }
}
