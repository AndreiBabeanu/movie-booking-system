package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.Auditorium;
import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.dto.AuditoriumRequest;
import unibuc.moviebooking.dto.CinemaRequest;

@Component
public class AuditoriumMapper {
    public Auditorium mapToEntity(AuditoriumRequest auditoriumRequest) {
        return new Auditorium(
                auditoriumRequest.getId(),
                auditoriumRequest.getCinemaId(),
                auditoriumRequest.getHall_number(),
                auditoriumRequest.getSeats()
        );
    }
}
