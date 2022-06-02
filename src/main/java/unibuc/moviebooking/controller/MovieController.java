package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.Movie;
import unibuc.moviebooking.dto.MovieRequest;
import unibuc.moviebooking.mapper.MovieMapper;
import unibuc.moviebooking.service.MovieService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/movies")
@Api(value = "/movies", tags = "Movie")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a Movie",
            notes = "Creates a new Movie based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Movie was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<Movie> create(
            @RequestBody
            @ApiParam(name = "movie", value = "Movie details", required = true)
                    MovieRequest movieRequest
    ) {
        Movie savedMovie = movieService.save(movieMapper.mapToEntity(movieRequest));

        return ResponseEntity
                .created(URI.create("/movies/" + savedMovie.getId()))
                .body(savedMovie);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get movie",
            notes = "Get a movie by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Movie was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<Movie> get(
            @PathVariable(name = "id")
            @ApiParam(name = "movieId", value = "Movie id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(movieService.getOne(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get movies",
            notes = "Get a list with all movies."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Movie list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<Movie>> getAll() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove movie",
            notes = "Remove a movie by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Movie was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> delete(
            @PathVariable(name = "id")
            @ApiParam(name = "movieId", value = "Movie id", required = true)
                    Long id
    ) {
        movieService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Movie was deleted successfully");
    }
}
