package unibuc.moviebooking.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CinemaRequest {
    private Long id;

    @NotNull
    private Long locationId;

    @NotBlank(message = "This field is required.")
    private String name;

}
