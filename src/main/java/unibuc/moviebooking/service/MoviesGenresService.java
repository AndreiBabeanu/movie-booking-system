package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Movie;
import unibuc.moviebooking.domain.MoviesGenres;
import unibuc.moviebooking.enums.Genres;
import unibuc.moviebooking.enums.GenresHelper;
import unibuc.moviebooking.repository.MoviesGenresRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesGenresService {

    private final MoviesGenresRepository moviesGenresRepository;

    public MoviesGenresService(MoviesGenresRepository moviesGenresRepository) {
        this.moviesGenresRepository = moviesGenresRepository;
    }

    public MoviesGenres save(MoviesGenres moviesGenres) {
        return moviesGenresRepository.save(moviesGenres);
    }

    public List<MoviesGenres> getGenresOfMovie(Long movieId) {
        List<MoviesGenres> movieGenres = this.moviesGenresRepository.getAllOfMovie(movieId);
        if (movieGenres.isEmpty()) {
            throw new RuntimeException("There is no genre for this movie saved!");
        }
        return movieGenres;
    }

    public List<MoviesGenres> getAll() {
        List<MoviesGenres> moviesGenres = moviesGenresRepository.getAll();
        if (moviesGenres.isEmpty()) {
            throw new RuntimeException("There is no genre for any movie saved!");
        }
        return moviesGenres;
    }

    public void deleteGenreForMovie(Long movieId, String type) {
        Genres genre = GenresHelper.convertToGenre(type);
        List<MoviesGenres> movieGenres = this.moviesGenresRepository.getAllOfMovie(movieId);
        Optional<MoviesGenres> moviesGenres = movieGenres.stream().filter(g -> g.getGenre().equals(genre)).findFirst();

        if (moviesGenres.isEmpty()) {
            throw new RuntimeException("This genre does not exist on this movie!");
        }

        this.moviesGenresRepository.deleteGenreForMovie(movieId, genre);
    }

}
