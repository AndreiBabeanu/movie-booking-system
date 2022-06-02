package unibuc.moviebooking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
    private Long id;
    private Long cinemaId;
    private String name;
    private int duration; // in minutes
}
