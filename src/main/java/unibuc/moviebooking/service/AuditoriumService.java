package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Auditorium;
import unibuc.moviebooking.exception.DuplicateAuditoriumException;
import unibuc.moviebooking.repository.AuditoriumRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;

    public AuditoriumService(AuditoriumRepository auditoriumRepository) {
        this.auditoriumRepository = auditoriumRepository;
    }

    public Auditorium save(Auditorium auditorium) {
        Optional<Auditorium> existingAuditorium = auditoriumRepository.getByHallNumberAndCinema(auditorium.getHall_number(), auditorium.getCinemaId());

        if (existingAuditorium.isPresent()) {
            throw new DuplicateAuditoriumException();
        }

        return auditoriumRepository.save(auditorium);
    }

    public List<Auditorium> getAll() {
        List<Auditorium> auditoriums = auditoriumRepository.getAll();
        if (auditoriums.isEmpty()) {
            throw new RuntimeException("There is no auditorium saved!");
        }
        return auditoriums;
    }

    public void delete(Long id) {
        Optional<Auditorium> auditorium = auditoriumRepository.getOne(id);

        if (auditorium.isEmpty()) {
            throw new NullPointerException("Auditorium not found!");
        }

        this.auditoriumRepository.delete(id);
    }

    public Auditorium getOne(Long id) {
        Optional<Auditorium> auditorium = auditoriumRepository.getOne(id);

        if (auditorium.isEmpty()) {
            throw new NullPointerException("Auditorium not found!");
        }

        return auditorium.get();
    }
}
