package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spittr.Spittle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcSpittleRepository implements SpittleRepository {

    JdbcOperations jdbcOperations;

    @Autowired
    public JdbcSpittleRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<Spittle> findSpittles(long maxId, int count) {
        return jdbcOperations.query("select * from Spittle where id < ? order by created_at desc limit ?",
                new SpittleRowMapper(), maxId, count);
    }

    public Spittle findOne(long spittleId) {
        Spittle spittle = jdbcOperations.queryForObject("select id, message, created_at, latitude, longitude from Spittle where id=?",
                new SpittleRowMapper(), spittleId);
        return spittle;

    }

    private static class SpittleRowMapper implements RowMapper<Spittle> {

        public Spittle mapRow(ResultSet rs, int i) throws SQLException {
            return new Spittle(
                    rs.getLong("id"),
                    rs.getString("message"),
                    rs.getDate("created_at"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude")
            );
        }
    }
}
