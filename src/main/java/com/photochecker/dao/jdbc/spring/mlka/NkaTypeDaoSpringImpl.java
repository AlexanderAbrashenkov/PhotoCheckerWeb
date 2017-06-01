package com.photochecker.dao.jdbc.spring.mlka;

import com.photochecker.dao.mlka.NkaTypeDao;
import com.photochecker.model.mlka.NkaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 31.05.2017.
 */
public class NkaTypeDaoSpringImpl implements NkaTypeDao {

    private final String SQL_FIND_BY_ID = "SELECT * FROM `nka_type_db` where `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `nka_type_db`";

    private final String SQL_SAVE = "INSERT INTO `nka_type_db` (`id`, `name`) VALUES (?, ?)";

    private final String SQL_FIND_BY_DATES = "select distinct n.`id`, n.`name` from `nka_type_db` n\n" +
            "inner join `client_card` cc on cc.`nka_type` = n.`id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 1;";


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NkaTypeDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<NkaType> nkaTypeRowMapper = (rs, rowNum) -> {
        return new NkaType(rs.getInt("id"),
                rs.getString("name"));
    };

    @Override
    public int save(NkaType nkaType) {
        return jdbcTemplate.update(SQL_SAVE,
                nkaType.getId(),
                nkaType.getName());
    }

    @Override
    public NkaType find(int id) {
        List<NkaType> result = jdbcTemplate.query(SQL_FIND_BY_ID, nkaTypeRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<NkaType> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, nkaTypeRowMapper);
    }

    @Override
    public boolean update(NkaType nkaType) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NkaType nkaType) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NkaType> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_BY_DATES, nkaTypeRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repTypeInd);
    }
}
