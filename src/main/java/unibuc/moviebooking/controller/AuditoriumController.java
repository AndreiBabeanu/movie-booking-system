package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.Auditorium;
import unibuc.moviebooking.dto.AuditoriumRequest;
import unibuc.moviebooking.mapper.AuditoriumMapper;
import unibuc.moviebooking.service.AuditoriumService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/auditoriums")
@Api(value = "/auditoriums", tags = "Auditorium")
public class AuditoriumController {
    private final AuditoriumService auditoriumService;
    private final AuditoriumMapper auditoriumMapper;

    public AuditoriumController(AuditoriumService auditoriumService, AuditoriumMapper auditoriumMapper) {
        this.auditoriumService = auditoriumService;
        this.auditoriumMapper = auditoriumMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a Auditorium",
            notes = "Creates a new Auditorium based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Auditorium was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Auditorium> create(
            @RequestBody
            @ApiParam(name = "auditorium", value = "Auditorium details", required = true)
                    AuditoriumRequest auditoriumRequest
    ) {
        Auditorium savedAuditorium = auditoriumService.save(auditoriumMapper.mapToEntity(auditoriumRequest));

        return ResponseEntity
                .created(URI.create("/auditoriums/" + savedAuditorium.getId()))
                .body(savedAuditorium);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get auditorium",
            notes = "Get a auditorium by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Auditorium was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<Auditorium> get(
            @PathVariable(name = "id")
            @ApiParam(name = "auditoriumId", value = "Auditorium id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(auditoriumService.getOne(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get auditoriums",
            notes = "Get a list with all auditoriums."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Auditorium list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<Auditorium>> getAll() {
        return ResponseEntity.ok(auditoriumService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove auditorium",
            notes = "Remove a auditorium by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Auditorium was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> delete(
            @PathVariable(name = "id")
            @ApiParam(name = "auditoriumId", value = "Auditorium id", required = true)
                    Long id
    ) {
        auditoriumService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Auditorium was deleted successfully");
    }
}
