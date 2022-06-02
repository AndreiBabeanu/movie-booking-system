package unibuc.moviebooking.mapper;

import org.springframework.stereotype.Component;
import unibuc.moviebooking.domain.Auditorium;
import unibuc.moviebooking.domain.Client;
import unibuc.moviebooking.dto.AuditoriumRequest;
import unibuc.moviebooking.dto.ClientRequest;

@Component
public class ClientMapper {
    public Client mapToEntity(ClientRequest clientRequest) {
        return new Client(
                clientRequest.getId(),
                clientRequest.getName(),
                clientRequest.getEmail()
        );
    }
}
