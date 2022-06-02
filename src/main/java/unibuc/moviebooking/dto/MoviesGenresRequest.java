package unibuc.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unibuc.moviebooking.enums.Genres;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MoviesGenresRequest {
    @NotNull
    private Long movieId;
    @NotNull
    private Genres genre;
}
