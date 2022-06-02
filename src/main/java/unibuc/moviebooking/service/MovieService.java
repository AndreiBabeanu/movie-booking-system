package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Movie;
import unibuc.moviebooking.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie save(Movie movie) {
        Optional<Movie> existingMovie = movieRepository.getByNameAndCinema(movie.getName(), movie.getCinemaId());

        if (existingMovie.isPresent()) {
            throw new RuntimeException("Duplicate movie. There is already an entry saved like this one.");
        }

        return movieRepository.save(movie);
    }

    public List<Movie> getAll() {
        List<Movie> movies = movieRepository.getAll();
        if (movies.isEmpty()) {
            throw new RuntimeException("There is no movie saved!");
        }
        return movies;
    }

    public void delete(Long id) {
        Optional<Movie> movie = movieRepository.getOne(id);

        if (movie.isEmpty()) {
            throw new NullPointerException("Movie not found!");
        }

        this.movieRepository.delete(id);
    }

    public Movie getOne(Long id) {
        Optional<Movie> movie = movieRepository.getOne(id);

        if (movie.isEmpty()) {
            throw new NullPointerException("Movie not found!");
        }

        return movie.get();
    }
}
