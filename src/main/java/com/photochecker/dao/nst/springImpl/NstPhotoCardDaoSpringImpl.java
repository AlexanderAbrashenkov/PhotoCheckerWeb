package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstPhotoCardDao;
import com.photochecker.model.nst.NstPhotoCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

@Component
public class NstPhotoCardDaoSpringImpl implements NstPhotoCardDao {

    private JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_BY_DATES = "select *\n" +
            "from `nst_photo` pc\n" +
            "where pc.`date` >= ? and pc.`date` < ?";

    private String SQL_FIND_ALL_SIMPLE = "select *\n" +
            "from %s";

    private final String SQL_SAVE = "INSERT INTO %s\n" +
            "(`client_id`, `url`, `date`)\n" +
            "VALUES (?, ?, ?);";

    private final String SQL_FIND_BY_DATES_NST = "select *\n" +
            "from `nst_photo` pc\n" +
            "inner join `nst_client_card` cc on cc.`id` = pc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`id` = ?\n" +
            "order by 2;";

    private final String SQL_FIND_ALL_BY_CLIENT_SIMPLE = "select *\n" +
            "from %s pc\n" +
            "inner join `nst_client_card` cc on cc.`id` = pc.`client_id`\n" +
            "where cc.`id` = ?\n" +
            "order by 2;";

    //language=SQL
    private final String SQL_COPY_DATA_FROM_TABLE = "INSERT INTO nst_photo (client_id, url, date) " +
            "SELECT client_id, url, date FROM %s";

    //language=SQL
    private final String SQL_DROP_TABLE = "DROP TABLE %s";

    //language=SQL
    private final String SQL_CREATE_NEW_TABLE = "CREATE TABLE %s LIKE nst_photo";

    @Autowired
    private Properties properties;

    @Autowired
    public NstPhotoCardDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NstPhotoCard> nstPhotoCardRowMapper = (resultSet, i) -> {
        return new NstPhotoCard(
                resultSet.getInt("client_id"),
                resultSet.getString("url"),
                resultSet.getTimestamp("date").toLocalDateTime());
    };

    @Override
    public int save(NstPhotoCard nstPhotoCard) {
        String sql = String.format(SQL_SAVE, properties.getProperty("nst.current.week.photo"));
        return jdbcTemplate.update(sql,
                nstPhotoCard.getClientId(),
                nstPhotoCard.getUrl(),
                Timestamp.valueOf(nstPhotoCard.getDate()));
    }

    @Override
    public NstPhotoCard find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstPhotoCard> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(NstPhotoCard photoCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NstPhotoCard photoCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstPhotoCard> findAllByDates(LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {
            String sql = String.format(SQL_FIND_ALL_SIMPLE, photoTableName);
            return jdbcTemplate.query(sql, nstPhotoCardRowMapper);
        } else {
            return jdbcTemplate.query(SQL_FIND_BY_DATES, nstPhotoCardRowMapper,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate.plusDays(1)));
        }
    }

    @Override
    public int copyPhotosToCommon(String photoTableName) {
        String sql = String.format(SQL_COPY_DATA_FROM_TABLE, photoTableName);
        int rows = jdbcTemplate.update(sql);

        String sql1 = String.format(SQL_DROP_TABLE, photoTableName);
        jdbcTemplate.update(sql1);
        return rows;
    }

    @Override
    public boolean createCurrentTable(String photoTableName) {
        String sql = String.format(SQL_CREATE_NEW_TABLE, photoTableName);
        int rows = jdbcTemplate.update(sql);
        return true;
    }

    @Override
    public List<NstPhotoCard> findAllByDatesNst(int clientId, LocalDate startDate, LocalDate endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {
            String sql = String.format(SQL_FIND_ALL_BY_CLIENT_SIMPLE, photoTableName);
            return jdbcTemplate.query(sql, nstPhotoCardRowMapper,
                    clientId);
        } else {
            return jdbcTemplate.query(SQL_FIND_BY_DATES_NST, nstPhotoCardRowMapper,
                    Date.valueOf(startDate),
                    Date.valueOf(endDate.plusDays(1)),
                    clientId);
        }
    }
}
