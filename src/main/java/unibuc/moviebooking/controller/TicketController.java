package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.Ticket;
import unibuc.moviebooking.dto.TicketRequest;
import unibuc.moviebooking.mapper.TicketMapper;
import unibuc.moviebooking.service.TicketService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/tickets")
@Api(value = "/tickets", tags = "Ticket")
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a Ticket",
            notes = "Creates a new Ticket based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Ticket was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Ticket> create(
            @RequestBody
            @ApiParam(name = "ticket", value = "Ticket details", required = true)
                    TicketRequest ticketRequest
    ) {
        Ticket savedTicket = ticketService.save(ticketMapper.mapToEntity(ticketRequest));

        return ResponseEntity
                .created(URI.create("/tickets/" + savedTicket.getId()))
                .body(savedTicket);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get ticket",
            notes = "Get a ticket by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ticket was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<Ticket> get(
            @PathVariable(name = "id")
            @ApiParam(name = "ticketId", value = "Ticket id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(ticketService.getOne(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get tickets",
            notes = "Get a list with all tickets."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ticket list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<Ticket>> getAll() {
        return ResponseEntity.ok(ticketService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove ticket",
            notes = "Remove a ticket by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Ticket was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> delete(
            @PathVariable(name = "id")
            @ApiParam(name = "ticketId", value = "Ticket id", required = true)
                    Long id
    ) {
        ticketService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Ticket was deleted successfully");
    }
}
