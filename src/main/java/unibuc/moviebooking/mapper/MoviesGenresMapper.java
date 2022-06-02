package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.Movie;
import unibuc.moviebooking.domain.MoviesGenres;
import unibuc.moviebooking.dto.MovieRequest;
import unibuc.moviebooking.dto.MoviesGenresRequest;

@Component
public class MoviesGenresMapper {
    public MoviesGenres mapToEntity(MoviesGenresRequest moviesGenresRequest) {
        return new MoviesGenres(
                moviesGenresRequest.getMovieId(),
                moviesGenresRequest.getGenre()
        );
    }
}
