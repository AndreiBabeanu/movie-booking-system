package unibuc.moviebooking.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ticket {
    private Long id;
    private Long screeningId;
    private Long clientId;
    private boolean isPaid;
}
