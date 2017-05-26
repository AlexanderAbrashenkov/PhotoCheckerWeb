package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.RegionDao;
import com.photochecker.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 18.05.2017.
 */
public class RegionDaoSpringImpl implements RegionDao {

    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_FIND_BY_ID = "SELECT * FROM `region_db`\n" +
            "WHERE `region_id` = ?";

    //language=SQL
    private final String SQL_FIND_ALL = "SELECT * FROM `region_db`";

    //language=SQL
    private final String SQL_FIND_BY_PARAMS = "select distinct r.`region_name`, r.`region_id` from `region_db` r\n" +
            "inner join `client_card` cc on cc.`region_id` = r.`region_id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 1;";

    private final String SQL_SAVE = "INSERT INTO `region_db` (`region_id`, `region_name`) VALUES (?, ?);";

    @Autowired
    public RegionDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Region> regionRowMapper = (resultSet, i) -> {
        return new Region(resultSet.getInt("region_id"),
                resultSet.getString("region_name").trim());
    };

    @Override
    public int save(Region region) {
        return jdbcTemplate.update(SQL_SAVE,
                region.getId(),
                region.getName());
    }

    @Override
    public Region find(int id) {
        List<Region> result = jdbcTemplate.query(SQL_FIND_BY_ID, regionRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Region> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, regionRowMapper);
    }

    @Override
    public boolean update(Region region) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(Region region) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<Region> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd) {
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, regionRowMapper,
                startDate,
                endDate,
                repTypeInd);
    }
}
