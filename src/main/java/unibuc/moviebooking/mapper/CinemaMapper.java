package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.dto.CinemaRequest;

@Component
public class CinemaMapper {
    public Cinema mapToEntity(CinemaRequest cinemaRequest) {
        return new Cinema(
                cinemaRequest.getId(),
                cinemaRequest.getLocationId(),
                cinemaRequest.getName()
        );
    }
}
