package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spittr.Spitter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    // interface defining operations implemented by JdbcTemplate(to keep application
    // loosely coupled to jdbcTemplate)
    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcSpitterRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    String INSERT_SPITTER = "insert into Spitter (username, password, first_name, last_name)" +
                            " values(?,?,?,?)";
    public Spitter save(Spitter spitter) {
        jdbcOperations.update(INSERT_SPITTER,
                spitter.getUsername(),
                spitter.getPassword(),
                spitter.getFirstName(),
                spitter.getLastName());
        return spitter;
    }

    public Spitter findByUsername(String user) {
        Spitter spitter = jdbcOperations.queryForObject("select id, username, null, first_name, last_name from Spitter where username=?",
                new SpitterRowMapper(), user);
        return spitter;
    }

    private static class SpitterRowMapper implements RowMapper<Spitter> {
        public Spitter mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Spitter(
                    resultSet.getLong("id"),
                    resultSet.getString("username"),
                    null,
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")
            );
        }
    }
}
