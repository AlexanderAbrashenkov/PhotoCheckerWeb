package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.RegionDao;
import com.photochecker.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
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

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    //language=SQL
    private final String SQL_FIND_BY_ID = "SELECT * FROM `region_db`\n" +
            "WHERE `region_id` = :region_id";

    //language=SQL
    private final String SQL_FIND_ALL = "SELECT * FROM `region_db`";

    //language=SQL
    private final String SQL_FIND_BY_PARAMS = "select distinct r.`region_name`, r.`region_id` from `region_db` r\n" +
            "inner join `client_card` cc on cc.`region_id` = r.`region_id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= :startDate and pc.`date` < :endDate\n" +
            "order by 1;";

    @Autowired
    public RegionDaoSpringImpl(DataSource dataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Region> regionRowMapper = (resultSet, i) -> {
        return new Region(resultSet.getInt("region_id"),
                resultSet.getString("region_name").trim());
    };

    @Override
    public int save(Region region) {
        return -1;
    }

    @Override
    public Region find(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("region_id", id);
        return namedParameterJdbcTemplate.query(SQL_FIND_BY_ID, map, regionRowMapper).get(0);
    }

    @Override
    public List<Region> findAll() {
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL, regionRowMapper);
    }

    @Override
    public boolean update(Region region) {
        return false;
    }

    @Override
    public void remove(Region region) {

    }

    @Override
    public List<Region> findAllByDates(LocalDate startDate, LocalDate endDate) {
        endDate = endDate.plusDays(1);
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return namedParameterJdbcTemplate.query(SQL_FIND_BY_PARAMS, map, regionRowMapper);
    }
}
