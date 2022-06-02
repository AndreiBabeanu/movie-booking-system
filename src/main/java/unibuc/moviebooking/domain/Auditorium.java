package unibuc.moviebooking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auditorium {
    private Long id;
    private Long cinemaId;
    private int hall_number;
    private int seats;
}
