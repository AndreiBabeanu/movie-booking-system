package unibuc.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieRequest {
    private Long id;
    @NotNull
    private Long cinemaId;
    @NotBlank
    private String name;
    @Range(min = 60, max = 180)
    private int duration; // in minutes
}
