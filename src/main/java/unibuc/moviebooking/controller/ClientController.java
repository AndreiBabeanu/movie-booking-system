package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.Client;
import unibuc.moviebooking.dto.ClientRequest;
import unibuc.moviebooking.mapper.ClientMapper;
import unibuc.moviebooking.service.ClientService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/clients")
@Api(value = "/clients", tags = "Client")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a Client",
            notes = "Creates a new Client based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Client was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Client> create(
            @RequestBody
            @ApiParam(name = "client", value = "Client details", required = true)
                    ClientRequest clientRequest
    ) {
        Client savedClient = clientService.save(clientMapper.mapToEntity(clientRequest));

        return ResponseEntity
                .created(URI.create("/clients/" + savedClient.getId()))
                .body(savedClient);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get client",
            notes = "Get a client by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Client was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<Client> get(
            @PathVariable(name = "id")
            @ApiParam(name = "clientId", value = "Client id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(clientService.getOne(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get clients",
            notes = "Get a list with all clients."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Client list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove client",
            notes = "Remove a client by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Client was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> delete(
            @PathVariable(name = "id")
            @ApiParam(name = "clientId", value = "Client id", required = true)
                    Long id
    ) {
        clientService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Client was deleted successfully");
    }
}
