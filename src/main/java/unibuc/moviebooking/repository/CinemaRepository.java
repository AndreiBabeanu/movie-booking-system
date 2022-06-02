package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.Cinema;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CinemaRepository implements DaoRepository<Cinema>{
    private final String CINEMAS = "cinemas";
    private final String ID = "id";
    private final String NAME = "name";
    private final String LOCATION_ID = "location_id";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CinemaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cinema save(Cinema cinema) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?)", CINEMAS);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, cinema.getLocationId());
            preparedStatement.setString(3, cinema.getName());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        cinema.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
        return cinema;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ?", CINEMAS, ID);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Cinema> getOne(Long id) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", CINEMAS, ID);

        RowMapper<Cinema> mapper = getCinemaRowMapper();

        return getCinemaFromResultSet(jdbcTemplate.query(sql, mapper, id));
    }

    public Optional<Cinema> getByNameAndLocation(String name, Long locationId) {
        String sql = String.format("SELECT * FROM %s c WHERE c.%s = ? AND c.%s = ?", CINEMAS, NAME, LOCATION_ID);

        RowMapper<Cinema> mapper = getCinemaRowMapper();

        return getCinemaFromResultSet(jdbcTemplate.query(sql, mapper, name, locationId));
    }

    @Override
    public List<Cinema> getAll() {
        String sql = String.format("SELECT * FROM %s", CINEMAS);

        RowMapper<Cinema> mapper = getCinemaRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private Optional<Cinema> getCinemaFromResultSet(List<Cinema> cinemas) {
        if(cinemas != null && !cinemas.isEmpty()) {
            return Optional.of(cinemas.get(0));
        } else {
            return Optional.empty();
        }
    }

    private RowMapper<Cinema> getCinemaRowMapper() {
        return (resultSet, rowNum) ->
                new Cinema(resultSet.getLong(ID),
                        resultSet.getLong(LOCATION_ID),
                        resultSet.getString(NAME)
                );
    }
}
