package com.photochecker.dao.nst.springImpl;

import com.photochecker.dao.nst.NstClientCardDao;
import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.model.nst.NstClientCard;
import com.photochecker.model.nst.NstObl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NstClientCardDaoSpringImpl implements NstClientCardDao {

    private final String SQL_FIND_ALL = "SELECT cc.*, 0 AS checked FROM `nst_client_card` cc";

    private final String SQL_FIND_BY_ID = "SELECT cc.*, 0 AS checked FROM `nst_client_card` cc " +
            "WHERE cc.`id` = ?";

    private final String SQL_FIND_BY_OBL_AND_DATES = "SELECT DISTINCT cc.*, \n" +
            "CASE WHEN sav.`save_date` IS NOT NULL THEN 1 ELSE 0 END checked\n" +
            "FROM `nst_client_card` cc\n" +
            "INNER JOIN `photo_card` pc ON pc.`client_id` = cc.`id`\n" +
            "INNER JOIN `nst_obl` obl ON obl.id = cc.obl_id\n" +
            "LEFT JOIN (SELECT slka.`client_id`, slka.`save_date` FROM `nst_save_db` slka WHERE slka.date_from=? AND slka.date_to=?) sav ON sav.client_id=cc.id\n" +
            "WHERE pc.`date` >= ? AND pc.`date` < ?\n" +
            "AND obl.id = ?\n" +
            "AND pc.`report_type` = ?\n" +
            "ORDER BY 2;";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private NstOblDao nstOblDao;

    private List<NstObl> nstOblList;

    @Autowired
    public NstClientCardDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("nst_client_card")
                .usingGeneratedKeyColumns("id");
    }

    private RowMapper<NstClientCard> nstClientCardRowMapper = (rs, rowNum) -> {
        int oblId = rs.getInt("obl_id");
        NstObl nstObl = nstOblList.stream()
                .filter(nstObl1 -> nstObl1.getId() == oblId)
                .findFirst()
                .get();

        return new NstClientCard(rs.getInt("id"),
                rs.getString("name"),
                nstObl,
                rs.getInt("checked"));
    };

    private void setNstClientCardFields() {
        nstOblList = nstOblDao.findAll();
    }

    @Override
    public int save(NstClientCard nstClientCard) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", nstClientCard.getName());
        params.put("obl_id", nstClientCard.getNstObl().getId());

        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public NstClientCard find(int id) {
        setNstClientCardFields();
        List<NstClientCard> result = jdbcTemplate.query(SQL_FIND_BY_ID, nstClientCardRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<NstClientCard> findAll() {
        setNstClientCardFields();
        return jdbcTemplate.query(SQL_FIND_ALL, nstClientCardRowMapper);
    }

    @Override
    public boolean update(NstClientCard nstClientCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NstClientCard nstClientCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<NstClientCard> findAllByOblAndDates(int oblId, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        setNstClientCardFields();
        return jdbcTemplate.query(SQL_FIND_BY_OBL_AND_DATES, nstClientCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                Date.valueOf(startDate),
                Date.valueOf(endDate.plusDays(1)),
                oblId,
                repTypeInd);
    }
}
