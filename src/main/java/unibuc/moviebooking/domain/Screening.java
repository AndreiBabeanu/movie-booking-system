package unibuc.moviebooking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Screening {
    private Long id;
    private Long movieId;
    private Long auditoriumId;
    private LocalDateTime startTime;
}
