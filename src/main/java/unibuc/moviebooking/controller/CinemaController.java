package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.dto.CinemaRequest;
import unibuc.moviebooking.mapper.CinemaMapper;
import unibuc.moviebooking.service.CinemaService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/cinemas")
@Api(value = "/cinemas", tags = "Cinemas")
public class CinemaController {
    private final CinemaService cinemaService;
    private final CinemaMapper cinemaMapper;

    public CinemaController(CinemaService cinemaService, CinemaMapper cinemaMapper) {
        this.cinemaService = cinemaService;
        this.cinemaMapper = cinemaMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a Cinema",
            notes = "Creates a new Cinema based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Cinema was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Cinema> create(
            @RequestBody
            @ApiParam(name = "cinema", value = "Cinema details", required = true)
                    CinemaRequest cinemaRequest
    ) {
        Cinema savedCinema = cinemaService.save(cinemaMapper.mapToEntity(cinemaRequest));

        return ResponseEntity
                .created(URI.create("/cinemas/" + savedCinema.getId()))
                .body(savedCinema);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get cinema",
            notes = "Get a cinema by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cinema was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<Cinema> getCinema(
            @PathVariable(name = "id")
            @ApiParam(name = "cinemaId", value = "Cinema id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(cinemaService.getOne(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get cinemas",
            notes = "Get a list with all cinemas."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cinema list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<Cinema>> getAll() {
        return ResponseEntity.ok(cinemaService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove cinema",
            notes = "Remove a cinema by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cinema was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> deleteCinema(
            @PathVariable(name = "id")
            @ApiParam(name = "cinemaId", value = "Cinema id", required = true)
                    Long id
    ) {
        cinemaService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cinema was deleted successfully");
    }
}
