package unibuc.moviebooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketRequest {
    private Long id;
    @NotNull
    private Long screeningId;
    @NotNull
    private Long clientId;
    @NotNull
    private boolean isPaid;
}
