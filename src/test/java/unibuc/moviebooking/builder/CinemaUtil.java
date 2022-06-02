package unibuc.moviebooking.builder;

import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.dto.CinemaRequest;

public class CinemaUtil {

    public static Cinema anCinema(Long id) {
        return Cinema.builder()
                .id(id)
                .locationId(2L)
                .name("Cinema Trivale")
                .build();
    }

    public static CinemaRequest aCinemaDto(Long id) {
        return CinemaRequest
                .builder()
                .id(id)
                .locationId(2L)
                .name("Cinema Trivale")
                .build();
    }
}
