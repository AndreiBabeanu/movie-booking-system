package unibuc.moviebooking.repository;

import java.util.List;
import java.util.Optional;

public interface DaoRepository<T> {

    T save(T object);

    void delete(Long id);

    Optional<T> getOne(Long id);

    List<T> getAll();
}
