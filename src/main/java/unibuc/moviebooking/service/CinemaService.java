package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.exception.DuplicateCinemaException;
import unibuc.moviebooking.repository.CinemaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public Cinema save(Cinema cinema) {
        Optional<Cinema> existingCinema = cinemaRepository.getByNameAndLocation(cinema.getName(), cinema.getLocationId());

        if (existingCinema.isPresent()) {
            throw new DuplicateCinemaException();
        }

        return cinemaRepository.save(cinema);
    }

    public List<Cinema> getAll() {
        List<Cinema> cinemas = cinemaRepository.getAll();
        if (cinemas.isEmpty()) {
            throw new RuntimeException("There is no cinema saved!");
        }
        return cinemas;
    }

    public void delete(Long id) {
        Optional<Cinema> cinema = cinemaRepository.getOne(id);

        if (cinema.isEmpty()) {
            throw new NullPointerException("Cinema not found!");
        }

        this.cinemaRepository.delete(id);
    }

    public Cinema getOne(Long id) {
        Optional<Cinema> cinema = cinemaRepository.getOne(id);

        if (cinema.isEmpty()) {
            throw new NullPointerException("Cinema not found!");
        }

        return cinema.get();
    }
}
