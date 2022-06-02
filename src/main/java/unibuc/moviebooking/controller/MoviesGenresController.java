package unibuc.moviebooking.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unibuc.moviebooking.domain.MoviesGenres;
import unibuc.moviebooking.dto.MovieRequest;
import unibuc.moviebooking.dto.MoviesGenresRequest;
import unibuc.moviebooking.mapper.MoviesGenresMapper;
import unibuc.moviebooking.service.MoviesGenresService;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/moviesGenres")
@Api(value = "/moviesGenres", tags = "MoviesGenres")
public class MoviesGenresController {
    private final MoviesGenresService moviesGenresService;
    private final MoviesGenresMapper moviesGenresMapper;

    public MoviesGenresController(MoviesGenresService moviesGenresService, MoviesGenresMapper moviesGenresMapper) {
        this.moviesGenresService = moviesGenresService;
        this.moviesGenresMapper = moviesGenresMapper;
    }

    @PostMapping
    @ApiOperation(
            value = "Create a MoviesGenres",
            notes = "Creates a new MoviesGenres based on the information received in the request"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The MoviesGenres was successfully created based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request")
    })
    public ResponseEntity<MoviesGenres> create(
            @RequestBody
            @ApiParam(name = "movieGenres", value = "MoviesGenres details", required = true)
                    MoviesGenresRequest moviesGenresRequest
    ) {
        MoviesGenres savedMoviesGenre = moviesGenresService.save(moviesGenresMapper.mapToEntity(moviesGenresRequest));

        return ResponseEntity
                .created(URI.create("/moviesGenres/" + savedMoviesGenre.getMovieId()))
                .body(savedMoviesGenre);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(
            value = "Get movieGenres",
            notes = "Get a movieGenres by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "MoviesGenres was retrieved successfully"),
            @ApiResponse(code = 500, message = "Error in the serve.")
    })
    public ResponseEntity<List<MoviesGenres>> getGenresOfMovie(
            @PathVariable(name = "id")
            @ApiParam(name = "movieId", value = "Movie id", required = true)
                    Long id
    ) {
        return ResponseEntity.ok(moviesGenresService.getGenresOfMovie(id));
    }

    @GetMapping()
    @ApiOperation(
            value = "Get moviesGenres",
            notes = "Get a list with all moviesGenres."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "MoviesGenres list was retrieved successfully."),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<List<MoviesGenres>> getAll() {
        return ResponseEntity.ok(moviesGenresService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(
            value = "Remove movieGenres",
            notes = "Remove a movieGenres by the id passed through url."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "MoviesGenres was deleted successfully"),
            @ApiResponse(code = 500, message = "Error in the server.")
    })
    public ResponseEntity<String> delete(
            @PathVariable(name = "id")
            @ApiParam(name = "movieId", value = "Movie id", required = true)
                    Long id,
            @RequestParam(value = "genre")
            @ApiParam(name = "genre", value = "Genre you want to delete for requeste movie", required = true)
                    String genre
    ) {
        moviesGenresService.deleteGenreForMovie(id, genre);
        return ResponseEntity.status(HttpStatus.OK).body("MoviesGenres was deleted successfully");
    }
}
