package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.DistrDao;
import com.photochecker.dao.common.RegionDao;
import com.photochecker.model.Distr;
import com.photochecker.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final String SQL_FIND_BY_DATES_AND_NKA = "select distinct d.`distr_name`, d.`distr_id`, d.`region_id` \n" +
            "from `distr_db` d\n" +
            "inner join `client_card` cc on cc.`distributor_id` = d.`distr_id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and pc.`report_type` = ?\n" +
            "and cc.`nka_type` = ?\n" +
            "order by 1;";

    private final String SQL_FIND_BY_DATES = "select distinct d.`distr_name`, d.`distr_id`, d.`region_id` \n" +
            "from `distr_db` d\n" +
            "inner join `client_card` cc on cc.`distributor_id` = d.`distr_id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 1;";

    private final String SQL_SAVE = "INSERT INTO `distr_db` (`distr_id`, `distr_name`, `region_id`) VALUES (?, ?, ?);";

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
        regionList = regionDao.findAll();
    }

    @Override
    public int save(Distr distr) {
        return jdbcTemplate.update(SQL_SAVE,
                distr.getId(),
                distr.getName(),
                distr.getRegion().getId());
    }

    @Override
    public Distr find(int id) {
        setDistrFields();
        List<Distr> result = jdbcTemplate.query(SQL_FIND_BY_ID, distrRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Distr> findAll() {
        setDistrFields();
        return jdbcTemplate.query(SQL_FIND_ALL, distrRowMapper);
    }

    @Override
    public boolean update(Distr distr) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(Distr distr) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<Distr> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd) {
        setDistrFields();
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_DATES, distrRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repTypeInd);
    }

    @Override
    public List<Distr> findAllByDatesAndNka(LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId) {
        setDistrFields();
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_DATES_AND_NKA, distrRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repTypeInd,
                nkaId);
    }
}
