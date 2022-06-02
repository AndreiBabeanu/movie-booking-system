package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.Screening;
import unibuc.moviebooking.dto.ScreeningRequest;
import unibuc.moviebooking.mapper.ScreeningMapper;
import unibuc.moviebooking.service.ScreeningService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/screenings")
@Api(value = "/screenings", tags = "Screening")
public class ScreeningController {
    private final ScreeningService screeningService;
    private final ScreeningMapper screeningMapper;

    public ScreeningController(ScreeningService screeningService, ScreeningMapper screeningMapper) {
        this.screeningService = screeningService;
        this.screeningMapper = screeningMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a Screening",
            notes = "Creates a new Screening based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Screening was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Screening> create(
            @RequestBody
            @ApiParam(name = "screening", value = "Screening details", required = true)
                    ScreeningRequest screeningRequest
    ) {
        Screening savedScreening = screeningService.save(screeningMapper.mapToEntity(screeningRequest));

        return ResponseEntity
                .created(URI.create("/screenings/" + savedScreening.getId()))
                .body(savedScreening);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get screening",
            notes = "Get a screening by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Screening was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<Screening> get(
            @PathVariable(name = "id")
            @ApiParam(name = "screeningId", value = "Screening id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(screeningService.getOne(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get screenings",
            notes = "Get a list with all screenings."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Screening list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<Screening>> getAll() {
        return ResponseEntity.ok(screeningService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove screening",
            notes = "Remove a screening by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Screening was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> delete(
            @PathVariable(name = "id")
            @ApiParam(name = "screeningId", value = "Screening id", required = true)
                    Long id
    ) {
        screeningService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Screening was deleted successfully");
    }
}
