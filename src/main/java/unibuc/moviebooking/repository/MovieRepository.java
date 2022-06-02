package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.Movie;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MovieRepository implements DaoRepository<Movie>{
    private final String MOVIES = "movies";
    private final String ID = "id";
    private final String NAME = "name";
    private final String CINEMA_ID = "cinema_id";
    private final String DURATION = "duration";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie save(Movie movie) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?, ?)", MOVIES);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, movie.getCinemaId());
            preparedStatement.setString(3, movie.getName());
            preparedStatement.setInt(4, movie.getDuration());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        movie.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
        return movie;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ?", MOVIES, ID);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Movie> getOne(Long id) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", MOVIES, ID);

        RowMapper<Movie> mapper = getMovieRowMapper();

        return getMovieFromResultSet(jdbcTemplate.query(sql, mapper, id));
    }

    public Optional<Movie> getByNameAndCinema(String name, Long cinemaId) {
        String sql = String.format("SELECT * FROM %s c WHERE c.%s = ? AND c.%s = ?", MOVIES, NAME, CINEMA_ID);

        RowMapper<Movie> mapper = getMovieRowMapper();

        return getMovieFromResultSet(jdbcTemplate.query(sql, mapper, name, cinemaId));
    }

    @Override
    public List<Movie> getAll() {
        String sql = String.format("SELECT * FROM %s", MOVIES);

        RowMapper<Movie> mapper = getMovieRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private Optional<Movie> getMovieFromResultSet(List<Movie> movies) {
        if(movies != null && !movies.isEmpty()) {
            return Optional.of(movies.get(0));
        } else {
            return Optional.empty();
        }
    }

    private RowMapper<Movie> getMovieRowMapper() {
        return (resultSet, rowNum) ->
                new Movie(resultSet.getLong(ID),
                        resultSet.getLong(CINEMA_ID),
                        resultSet.getString(NAME),
                        resultSet.getInt(DURATION)
                );
    }
}
