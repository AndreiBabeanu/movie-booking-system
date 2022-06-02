package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Client;
import unibuc.moviebooking.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        Optional<Client> existingClient = clientRepository.getByEmail(client.getEmail());

        if (existingClient.isPresent()) {
            throw new RuntimeException("Duplicate client. There is already an entry saved like this one.");
        }

        return clientRepository.save(client);
    }

    public List<Client> getAll() {
        List<Client> clients = clientRepository.getAll();
        if (clients.isEmpty()) {
            throw new RuntimeException("There is no client saved!");
        }
        return clients;
    }

    public void delete(Long id) {
        Optional<Client> client = clientRepository.getOne(id);

        if (client.isEmpty()) {
            throw new NullPointerException("Client not found!");
        }

        this.clientRepository.delete(id);
    }

    public Client getOne(Long id) {
        Optional<Client> client = clientRepository.getOne(id);

        if (client.isEmpty()) {
            throw new NullPointerException("Client not found!");
        }

        return client.get();
    }
}
