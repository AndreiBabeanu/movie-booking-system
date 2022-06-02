package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.Client;
import unibuc.moviebooking.domain.Movie;
import unibuc.moviebooking.dto.ClientRequest;
import unibuc.moviebooking.dto.MovieRequest;

@Component
public class MovieMapper {
    public Movie mapToEntity(MovieRequest movieRequest) {
        return new Movie(
                movieRequest.getId(),
                movieRequest.getCinemaId(),
                movieRequest.getName(),
                movieRequest.getDuration()
        );
    }
}
