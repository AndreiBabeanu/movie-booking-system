package unibuc.moviebooking.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Cinema {
    private Long id;
    private Long locationId;
    private String name;
}
