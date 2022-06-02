package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.MoviesGenres;
import unibuc.moviebooking.domain.Screening;
import unibuc.moviebooking.dto.MoviesGenresRequest;
import unibuc.moviebooking.dto.ScreeningRequest;

@Component
public class ScreeningMapper {
    public Screening mapToEntity(ScreeningRequest screeningRequest) {
        return new Screening(
                screeningRequest.getId(),
                screeningRequest.getMovieId(),
                screeningRequest.getAuditoriumId(),
                screeningRequest.getStartTime()
        );
    }
}
