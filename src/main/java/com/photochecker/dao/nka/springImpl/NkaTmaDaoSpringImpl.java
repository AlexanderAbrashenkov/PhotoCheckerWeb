package com.photochecker.dao.nka.springImpl;

import com.photochecker.dao.common.FormatTypeDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.dao.nka.NkaParamDao;
import com.photochecker.dao.nka.NkaTmaDao;
import com.photochecker.model.common.FormatType;
import com.photochecker.model.common.Lka;
import com.photochecker.model.nka.NkaParam;
import com.photochecker.model.nka.NkaTma;
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

@Component
public class NkaTmaDaoSpringImpl implements NkaTmaDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private final String SQL_FIND_BY_ID = "SELECT * FROM `nka_tma`\n" +
            "WHERE `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `nka_tma`";

    //language=SQL
    private final String SQL_DELETE_ALL = "TRUNCATE nka_tma";

    //language=SQL
    private final String SQL_FIND_BY_NKA_AND_FORMAT = "SELECT * FROM nka_tma t " +
            "WHERE t.format_id = ? " +
            "AND t.lka_id = ?";

    @Autowired
    private LkaDao lkaDao;
    @Autowired
    private FormatTypeDao formatTypeDao;

    private List<Lka> lkaList;
    private List<FormatType> formatTypeList;

    @Autowired
    public NkaTmaDaoSpringImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("nka_tma")
                .usingGeneratedKeyColumns("id");
    }

    private void setEntityFields() {
        lkaList = lkaDao.findAllNka();
        formatTypeList = formatTypeDao.findAll();
    }

    private RowMapper<NkaTma> nkaTmaRowMapper = (resultSet, i) -> {

        int lkaId = resultSet.getInt("lka_id");
        int formatId = resultSet.getInt("format_id");

        Lka lka = lkaList.stream()
                .filter(lka1 -> lka1.getId() == lkaId)
                .findFirst()
                .get();

        FormatType formatType = formatTypeList.stream()
                .filter(formatType1 -> formatType1.getId() == formatId)
                .findFirst()
                .get();

        return new NkaTma(
                lka,
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDate("end_date").toLocalDate(),
                formatType,
                resultSet.getString("tg_name"),
                resultSet.getInt("sku_count"),
                resultSet.getString("comment")
        );
    };

    @Override
    public int save(NkaTma nkaTma) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("lka_id", nkaTma.getLka().getId());
        params.put("start_date", Date.valueOf(nkaTma.getStartDate()));
        params.put("end_date", Date.valueOf(nkaTma.getEndDate()));
        params.put("format_id", nkaTma.getFormatType().getId());
        params.put("tg_name", nkaTma.getTgName());
        params.put("sku_count", nkaTma.getSkuCount());
        params.put("comment", nkaTma.getComment());
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public NkaTma find(int id) {
        setEntityFields();
        List<NkaTma> result = jdbcTemplate.query(SQL_FIND_BY_ID, nkaTmaRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<NkaTma> findAll() {
        setEntityFields();
        return jdbcTemplate.query(SQL_FIND_ALL, nkaTmaRowMapper);
    }

    @Override
    public boolean update(NkaTma nkaTma) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NkaTma nkaTma) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void clearAll() {
        jdbcTemplate.update(SQL_DELETE_ALL);
    }

    @Override
    public List<NkaTma> findAllByNkaAndFormat(int nkaId, int formatId) {
        setEntityFields();
        return jdbcTemplate.query(SQL_FIND_BY_NKA_AND_FORMAT, nkaTmaRowMapper,
                formatId,
                nkaId);
    }
}
