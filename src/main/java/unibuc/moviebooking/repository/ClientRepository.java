package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.Cinema;
import unibuc.moviebooking.domain.Client;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ClientRepository implements DaoRepository<Client>{
    private final String CLIENTS = "clients";
    private final String ID = "id";
    private final String NAME = "name";
    private final String EMAIL = "email";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Client save(Client client) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?)", CLIENTS);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getEmail());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        client.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
        return client;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ?", CLIENTS, ID);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Client> getOne(Long id) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", CLIENTS, ID);

        RowMapper<Client> mapper = getClientRowMapper();

        return getClientFromResultSet(jdbcTemplate.query(sql, mapper, id));
    }

    public Optional<Client> getByEmail(String email) {
        String sql = String.format("SELECT * FROM %s c WHERE c.%s = ?", CLIENTS, EMAIL);

        RowMapper<Client> mapper = getClientRowMapper();

        return getClientFromResultSet(jdbcTemplate.query(sql, mapper, email));
    }

    @Override
    public List<Client> getAll() {
        String sql = String.format("SELECT * FROM %s", CLIENTS);

        RowMapper<Client> mapper = getClientRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private Optional<Client> getClientFromResultSet(List<Client> clients) {
        if(clients != null && !clients.isEmpty()) {
            return Optional.of(clients.get(0));
        } else {
            return Optional.empty();
        }
    }

    private RowMapper<Client> getClientRowMapper() {
        return (resultSet, rowNum) ->
                new Client(
                        resultSet.getLong(ID),
                        resultSet.getString(EMAIL),
                        resultSet.getString(NAME)
                );
    }
}
