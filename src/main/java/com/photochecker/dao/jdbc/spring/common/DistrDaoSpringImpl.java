package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.RegionDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 18.05.2017.
 */
public class DistrDaoSpringImpl implements DistrDao {

    private final String SQL_FIND_BY_ID = "SELECT * FROM `distr_db`\n" +
            "WHERE `distr_id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `distr_db`";

    private final String SQL_FIND_BY_PARAMS = "select distinct d.`distr_name`, d.`distr_id`, d.`region_id` \n" +
            "from `distr_db` d\n" +
            "inner join `client_card` cc on cc.`distributor_id` = d.`distr_id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "order by 1;";

    private JdbcTemplate jdbcTemplate;
    private List<Region> regionList;

    @Autowired
    private RegionDao regionDao;

    private RowMapper<Distr> distrRowMapper = (resultSet, i) -> {
        int region_id = resultSet.getInt("region_id");
        Region region = regionList.stream()
                .filter(region1 -> region1.getId() == region_id)
                .findFirst()
                .get();
        return new Distr(resultSet.getInt("distr_id"),
                resultSet.getString("distr_name"),
                region
        );
    };

    @Autowired
    public DistrDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void setDistrFields() {
        /*ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        regionList = ((RegionDao) context.getBean("regionDao")).findAll();*/
        regionList = regionDao.findAll();
    }

    @Override
    public int save(Distr distr) {
        return 0;
    }

    @Override
    public Distr find(int id) {
        setDistrFields();
        return jdbcTemplate.query(SQL_FIND_BY_ID, distrRowMapper, id).get(0);
    }

    @Override
    public List<Distr> findAll() {
        setDistrFields();
        return jdbcTemplate.query(SQL_FIND_ALL, distrRowMapper);
    }

    @Override
    public boolean update(Distr distr) {
        return false;
    }

    @Override
    public void remove(Distr distr) {

    }

    @Override
    public List<Distr> findAllByDates(LocalDate startDate, LocalDate endDate) {
        setDistrFields();
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, distrRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }
}
