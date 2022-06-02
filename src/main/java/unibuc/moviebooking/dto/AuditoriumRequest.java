package unibuc.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuditoriumRequest {
    private Long id;
    @NotNull
    private Long cinemaId;
    @Positive
    private int hall_number;
    @Positive
    private int seats;
}
