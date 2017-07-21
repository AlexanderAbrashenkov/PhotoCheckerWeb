package com.photochecker.dao.nst.hibernateImpl;

import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.model.nst.NstObl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Repository
public class NstOblHibernateImpl implements NstOblDao {

    /*private final String SQL_FIND_BY_DATES = "SELECT DISTINCT obl.`name`, obl.`id`\n" +
            "FROM `nst_obl` obl\n" +
            "INNER JOIN `nst_client_card` cc ON cc.`obl_id` = obl.`id`\n" +
            "INNER JOIN `nst_photo` pc ON pc.`client_id` = cc.`id`\n" +
            "WHERE pc.`date` >= :startDate AND pc.`date` < :endDate\n" +
            "AND cc.`format_id` = :formatId\n" +
            "ORDER BY 1;";*/

    private final String SQL_FIND_BY_DATES = "SELECT DISTINCT obl " +
            "FROM NstObl obl " +
            "JOIN NstClientCard cc " +
            "JOIN NstPhotoCard pc " +
            "WHERE pc.date >= :startDate AND pc.date < :endDate " +
            "AND cc.format_id = :formatId " +
            "ORDER BY obl.name";

    //language=SQL
    private final String SQL_FIND_ALL_SIMPLE = "SELECT DISTINCT obl " +
            "FROM NstObl obl " +
            "JOIN NstClientCard cc " +
            "JOIN %s pc " +
            "WHERE cc.format_id = :formatId " +
            "ORDER BY obl.name";

    @Autowired
    private Properties properties;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public NstOblHibernateImpl(DataSource dataSource) {
    }

    @Override
    @Transactional
    public int save(NstObl nstObl) {
        entityManager.persist(nstObl);
        entityManager.flush();
        return nstObl.getId();
    }

    @Override
    @Transactional
    public NstObl find(int id) {
        return entityManager.find(NstObl.class, id);
    }

    @Override
    @Transactional
    public List<NstObl> findAll() {
        return entityManager.createQuery("select o from NstObl o", NstObl.class)
                .getResultList();
    }

    @Override
    public boolean update(NstObl nstObl) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NstObl nstObl) {
        throw new RuntimeException("This method not used");
    }

    @Override
    @Transactional
    public List<NstObl> findAllByDates(LocalDate startDate, LocalDate endDate, int formatId, int repTypeInd) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {

            String sql = String.format(SQL_FIND_ALL_SIMPLE, photoTableName);
            return entityManager.createQuery(sql, NstObl.class)
                    .setParameter("formatId", formatId)
                    .getResultList();

        } else {
            return entityManager.createQuery(SQL_FIND_BY_DATES, NstObl.class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .setParameter("formatId", formatId)
                    .getResultList();
        }
    }
}
