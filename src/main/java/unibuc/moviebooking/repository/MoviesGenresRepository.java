package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.MoviesGenres;
import unibuc.moviebooking.enums.Genres;
import unibuc.moviebooking.enums.GenresHelper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class MoviesGenresRepository implements DaoRepository<MoviesGenres>{
    private final String MOVIES_GENRES = "movies_genres";
    private final String GENRE = "genre";
    private final String MOVIE_ID = "location_id";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MoviesGenresRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MoviesGenres save(MoviesGenres moviesGenres) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?)", MOVIES_GENRES);

        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
            preparedStatement.setLong(2, moviesGenres.getMovieId());
            preparedStatement.setString(3, GenresHelper.convertToString(moviesGenres.getGenre()));
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        return moviesGenres;
    }

    @Override
    public void delete(Long movieId) {
        throw new RuntimeException("Not implemented!");
    }

    public void deleteGenreForMovie(Long movieId, Genres genre) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ? AND c.%s = ?", MOVIES_GENRES, MOVIE_ID, GENRE);

        jdbcTemplate.update(sql, movieId, GenresHelper.convertToString(genre));
    }

    @Override
    public Optional<MoviesGenres> getOne(Long id) {
        throw new RuntimeException("Not performing this operation");
    }

    public List<MoviesGenres> getAllOfMovie(Long movieId) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", MOVIES_GENRES, MOVIE_ID);

        RowMapper<MoviesGenres> mapper = getMoviesGenresRowMapper();

        return jdbcTemplate.query(sql, mapper, movieId);
    }

    @Override
    public List<MoviesGenres> getAll() {
        String sql = String.format("SELECT * FROM %s", MOVIES_GENRES);

        RowMapper<MoviesGenres> mapper = getMoviesGenresRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private RowMapper<MoviesGenres> getMoviesGenresRowMapper() {
        return (resultSet, rowNum) ->
                new MoviesGenres(
                        resultSet.getLong(MOVIE_ID),
                        GenresHelper.convertToGenre(resultSet.getString(GENRE))
                );
    }
}
