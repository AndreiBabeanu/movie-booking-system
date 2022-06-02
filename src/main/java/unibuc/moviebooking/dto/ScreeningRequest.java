package unibuc.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ScreeningRequest {
    private Long id;
    @NotNull
    private Long movieId;
    @NotNull
    private Long auditoriumId;
    @NotNull
    private LocalDateTime startTime;
}
