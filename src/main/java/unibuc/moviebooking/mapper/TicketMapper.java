package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.Screening;
import unibuc.moviebooking.domain.Ticket;
import unibuc.moviebooking.dto.ScreeningRequest;
import unibuc.moviebooking.dto.TicketRequest;

@Component
public class TicketMapper {
    public Ticket mapToEntity(TicketRequest ticketRequest) {
        return new Ticket(
                ticketRequest.getId(),
                ticketRequest.getScreeningId(),
                ticketRequest.getClientId(),
                ticketRequest.isPaid()
        );
    }
}
