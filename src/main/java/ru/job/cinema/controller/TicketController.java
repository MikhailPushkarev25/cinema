package ru.job.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job.cinema.model.Session;
import ru.job.cinema.model.Ticket;
import ru.job.cinema.service.SessionService;
import ru.job.cinema.service.TicketService;


@Controller
public class TicketController {

    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @GetMapping("/films")
    public String viewTicket(Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "films";
    }
    @GetMapping("/formDeleteTicket/{ticketId}")
    public String deleteTicket(Model model, @PathVariable("ticketId") int id) {
        viewTicket(model, id);
        return "deleteTicket";
    }

    @GetMapping("/fromAddSeanse")
    public String addSeanse(Model model) {
        model.addAttribute("sessions", sessionService.findAllSession());
        return "addSeanse";
    }

    @GetMapping("/formUpdateTicket/{ticketId}")
    public String formUpdateTicket(Model model, @PathVariable("ticketId") int id) {
        viewTicket(model, id);
        return "updateTicket";
    }

    @PostMapping("/ticketDelete")
    public String formDeleteTicket(@ModelAttribute Ticket ticket) {
        ticket.setSession_id(sessionService.findById(ticket.getSession_id().getId()));
        ticketService.delete(ticket);
        return "redirect:/films";
    }

    @PostMapping("/createSeanse")
    public String createSeanse(@ModelAttribute Ticket ticket) {
        ticket.setSession_id(sessionService.findById(ticket.getSession_id().getId()));
        ticketService.add(ticket);
        return "redirect:/films";
    }

    @PostMapping("/ticketUpdate")
    public String ticketUpdate(@ModelAttribute Ticket ticket) {
        ticket.setSession_id(sessionService.findById(ticket.getSession_id().getId()));
        ticketService.update(ticket);
        return "redirect:/films";
    }

    private void viewTicket(Model model, @PathVariable("ticketId") int id) {
        Ticket ticket = ticketService.findById(id);
        Session session = ticket.getSession_id();
        if (session != null) {
            ticket.setSession_id(sessionService.findById(session.getId()));
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("sessions", sessionService.findAllSession());
    }
}
