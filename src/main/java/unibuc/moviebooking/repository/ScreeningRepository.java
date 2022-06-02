package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.Screening;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ScreeningRepository implements DaoRepository<Screening>{
    private final String SCREENING = "screening";
    private final String ID = "id";
    private final String MOVIE_ID = "movie_id";
    private final String AUDITORIUM_ID = "auditorium_id";
    private final String START_TIME = "start_time";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScreeningRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Screening save(Screening screening) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?, ?)", SCREENING);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, screening.getMovieId());
            preparedStatement.setLong(3, screening.getAuditoriumId());
            preparedStatement.setTimestamp(4, new Timestamp(screening.getStartTime().toEpochSecond(ZoneOffset.UTC)));

            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        screening.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
        return screening;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ?", SCREENING, ID);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Screening> getOne(Long id) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", SCREENING, ID);

        RowMapper<Screening> mapper = getScreeningRowMapper();

        return getScreeningFromResultSet(jdbcTemplate.query(sql, mapper, id));
    }

    public Optional<Screening> getByMovieAndAuditoriumAndTime(Long movieId, Long auditoriumId, LocalDateTime startTime) {
        String sql = String.format("SELECT * FROM %s c WHERE c.%s = ? AND c.%s = ? AND c.%s = ?", SCREENING, MOVIE_ID, AUDITORIUM_ID, START_TIME);

        RowMapper<Screening> mapper = getScreeningRowMapper();

        return getScreeningFromResultSet(jdbcTemplate.query(sql, mapper, movieId, auditoriumId, new Timestamp(startTime.toEpochSecond(ZoneOffset.UTC))));
    }

    @Override
    public List<Screening> getAll() {
        String sql = String.format("SELECT * FROM %s", SCREENING);

        RowMapper<Screening> mapper = getScreeningRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private Optional<Screening> getScreeningFromResultSet(List<Screening> screenings) {
        if(screenings != null && !screenings.isEmpty()) {
            return Optional.of(screenings.get(0));
        } else {
            return Optional.empty();
        }
    }

    private RowMapper<Screening> getScreeningRowMapper() {
        return (resultSet, rowNum) ->
                new Screening(
                        resultSet.getLong(ID),
                        resultSet.getLong(AUDITORIUM_ID),
                        resultSet.getLong(MOVIE_ID),
                        resultSet.getTimestamp(START_TIME).toLocalDateTime()
                );
    }
}
