package unibuc.moviebooking.service;

import org.springframework.stereotype.Service;
import unibuc.moviebooking.domain.Screening;
import unibuc.moviebooking.repository.ScreeningRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public Screening save(Screening screening) {
        Optional<Screening> existingScreening = screeningRepository.getByMovieAndAuditoriumAndTime(screening.getMovieId(), screening.getAuditoriumId(), screening.getStartTime());

        if (existingScreening.isPresent()) {
            throw new RuntimeException("Duplicate screening. There is already an entry saved like this one.");
        }

        return screeningRepository.save(screening);
    }

    public List<Screening> getAll() {
        List<Screening> screenings = screeningRepository.getAll();
        if (screenings.isEmpty()) {
            throw new RuntimeException("There is no screening saved!");
        }
        return screenings;
    }

    public void delete(Long id) {
        Optional<Screening> screening = screeningRepository.getOne(id);

        if (screening.isEmpty()) {
            throw new NullPointerException("Screening not found!");
        }

        this.screeningRepository.delete(id);
    }

    public Screening getOne(Long id) {
        Optional<Screening> screening = screeningRepository.getOne(id);

        if (screening.isEmpty()) {
            throw new NullPointerException("Screening not found!");
        }

        return screening.get();
    }
}
