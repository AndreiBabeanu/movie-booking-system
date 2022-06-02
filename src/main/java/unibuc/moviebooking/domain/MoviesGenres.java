package unibuc.moviebooking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import unibuc.moviebooking.enums.Genres;

@Data
@AllArgsConstructor
public class MoviesGenres {
    private Long movieId;
    private Genres genre;
}
