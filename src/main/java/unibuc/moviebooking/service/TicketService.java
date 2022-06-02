package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Ticket;
import unibuc.moviebooking.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAll() {
        List<Ticket> tickets = ticketRepository.getAll();
        if (tickets.isEmpty()) {
            throw new RuntimeException("There is no ticket saved!");
        }
        return tickets;
    }

    public void delete(Long id) {
        Optional<Ticket> ticket = ticketRepository.getOne(id);

        if (ticket.isEmpty()) {
            throw new NullPointerException("Ticket not found!");
        }

        this.ticketRepository.delete(id);
    }

    public Ticket getOne(Long id) {
        Optional<Ticket> ticket = ticketRepository.getOne(id);

        if (ticket.isEmpty()) {
            throw new NullPointerException("Ticket not found!");
        }

        return ticket.get();
    }
}
