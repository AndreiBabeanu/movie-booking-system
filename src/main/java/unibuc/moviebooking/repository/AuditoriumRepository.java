package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.Auditorium;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuditoriumRepository implements DaoRepository<Auditorium>{
    private final String AUDITORIUM = "auditorium";
    private final String ID = "id";
    private final String HALL_NUMBER = "hall_number";
    private final String CINEMA_ID = "cinema_id";
    private final String SEATS = "seats";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditoriumRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Auditorium save(Auditorium auditorium) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?)", AUDITORIUM);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, auditorium.getCinemaId());
            preparedStatement.setInt(3, auditorium.getHall_number());
            preparedStatement.setInt(4, auditorium.getSeats());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        auditorium.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
        return auditorium;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ?", AUDITORIUM, ID);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Auditorium> getOne(Long id) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", AUDITORIUM, ID);

        RowMapper<Auditorium> mapper = getAuditoriumRowMapper();

        return getAuditoriumFromResultSet(jdbcTemplate.query(sql, mapper, id));
    }

    public Optional<Auditorium> getByHallNumberAndCinema(int hall_number, Long cinemaId) {
        String sql = String.format("SELECT * FROM %s c WHERE c.%s = ? AND c.%s = ?", AUDITORIUM, HALL_NUMBER, CINEMA_ID);

        RowMapper<Auditorium> mapper = getAuditoriumRowMapper();

        return getAuditoriumFromResultSet(jdbcTemplate.query(sql, mapper, hall_number, cinemaId));
    }

    @Override
    public List<Auditorium> getAll() {
        String sql = String.format("SELECT * FROM %s", AUDITORIUM);

        RowMapper<Auditorium> mapper = getAuditoriumRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private Optional<Auditorium> getAuditoriumFromResultSet(List<Auditorium> auditoriums) {
        if(auditoriums != null && !auditoriums.isEmpty()) {
            return Optional.of(auditoriums.get(0));
        } else {
            return Optional.empty();
        }
    }

    private RowMapper<Auditorium> getAuditoriumRowMapper() {
        return (resultSet, rowNum) ->
                new Auditorium(
                        resultSet.getLong(ID),
                        resultSet.getLong(CINEMA_ID),
                        resultSet.getInt(HALL_NUMBER),
                        resultSet.getInt(SEATS)
                );
    }
}
