package unibuc.moviebooking.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import unibuc.moviebooking.domain.Ticket;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TicketRepository implements DaoRepository<Ticket>{
    private final String TICKETS = "tickets";
    private final String ID = "id";
    private final String SCREENING_ID = "screening_id";
    private final String CLIENT_ID = "client_id";
    private final String IS_PAID = "is_paid";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ticket save(Ticket ticket) {
        String sql = String.format("INSERT INTO %s VALUES (?, ?, ?, ?)", TICKETS);
        PreparedStatementCreator preparedStatementCreator = (connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setObject(1, null);
            preparedStatement.setLong(2, ticket.getScreeningId());
            preparedStatement.setLong(3, ticket.getClientId());
            preparedStatement.setBoolean(4, ticket.isPaid());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);

        ticket.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).longValue());
        return ticket;
    }

    @Override
    public void delete(Long id) {
        String sql = String.format("DELETE FROM %s c WHERE c.%s = ?", TICKETS, ID);

        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Ticket> getOne(Long id) {
        String sql = String.format("SELECT * FROM %s l WHERE l.%s = ?", TICKETS, ID);

        RowMapper<Ticket> mapper = getScreeningRowMapper();

        return getScreeningFromResultSet(jdbcTemplate.query(sql, mapper, id));
    }

    @Override
    public List<Ticket> getAll() {
        String sql = String.format("SELECT * FROM %s", TICKETS);

        RowMapper<Ticket> mapper = getScreeningRowMapper();

        return jdbcTemplate.query(sql, mapper);
    }

    private Optional<Ticket> getScreeningFromResultSet(List<Ticket> tickets) {
        if(tickets != null && !tickets.isEmpty()) {
            return Optional.of(tickets.get(0));
        } else {
            return Optional.empty();
        }
    }

    private RowMapper<Ticket> getScreeningRowMapper() {
        return (resultSet, rowNum) ->
                new Ticket(
                        resultSet.getLong(ID),
                        resultSet.getLong(CLIENT_ID),
                        resultSet.getLong(SCREENING_ID),
                        resultSet.getBoolean(IS_PAID)
                );
    }
}
