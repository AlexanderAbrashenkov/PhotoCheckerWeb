package com.photochecker.dao.nst.hibernateImpl;

import com.photochecker.dao.nst.NstClientCardDao;
import com.photochecker.dao.nst.NstFormatDao;
import com.photochecker.dao.nst.NstOblDao;
import com.photochecker.model.nst.NstClientCard;
import com.photochecker.model.nst.NstFormat;
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
public class NstClientCardHibernateImpl implements NstClientCardDao {

    private final String SQL_FIND_BY_OBL_AND_DATES = "SELECT DISTINCT cc.*, \n" +
            "CASE WHEN sav.`save_date` IS NOT NULL THEN 1 ELSE 0 END checked\n" +
            "FROM `nst_client_card` cc\n" +
            "INNER JOIN `nst_photo` pc ON pc.`client_id` = cc.`id`\n" +
            "INNER JOIN `nst_obl` obl ON obl.id = cc.obl_id\n" +
            "LEFT JOIN (SELECT slka.`client_id`, slka.`save_date` FROM `nst_save_db` slka WHERE slka.date_from=:startDate AND slka.date_to=:endDate) sav ON sav.client_id=cc.id\n" +
            "WHERE pc.`date` >= :startDate1 AND pc.`date` < :endDate1\n" +
            "AND cc.format_id = :formatId\n" +
            "AND obl.id = :oblId\n" +
            "ORDER BY 2;";

    //language=SQL
    private final String SQL_FIND_ALL_SIMPLE = "SELECT DISTINCT cc.*, \n" +
            "CASE WHEN sav.`save_date` IS NOT NULL THEN 1 ELSE 0 END checked\n" +
            "FROM `nst_client_card` cc\n" +
            "INNER JOIN %s pc ON pc.`client_id` = cc.`id`\n" +
            "INNER JOIN `nst_obl` obl ON obl.id = cc.obl_id\n" +
            "LEFT JOIN (SELECT slka.`client_id`, slka.`save_date` FROM %s slka) sav ON sav.client_id=cc.id\n" +
            "WHERE cc.format_id = :formatId\n" +
            "AND obl.id = :oblId\n" +
            "ORDER BY 2;";

    @Autowired
    private Properties properties;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public NstClientCardHibernateImpl(DataSource dataSource) {
    }

    @Override
    @Transactional
    public int save(NstClientCard nstClientCard) {
        entityManager.persist(nstClientCard);
        entityManager.flush();
        return nstClientCard.getId();
    }

    @Override
    @Transactional
    public NstClientCard find(int id) {
        return entityManager.find(NstClientCard.class, id);
    }

    @Override
    @Transactional
    public List<NstClientCard> findAll() {
        return entityManager.createQuery("SELECT c FROM NstClientCard c", NstClientCard.class)
                .getResultList();
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
    @Transactional
    public List<NstClientCard> findAllByOblAndDates(int formatId, int oblId, LocalDate startDate, LocalDate endDate, int repTypeInd) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {

            String saveTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_save";
            String sql = String.format(SQL_FIND_ALL_SIMPLE, photoTableName, saveTableName);
            return entityManager.createNativeQuery(sql, NstClientCard.class)
                    .setParameter("formatId", formatId)
                    .setParameter("oblId", oblId)
                    .getResultList();

        } else {
            return entityManager.createNativeQuery(SQL_FIND_BY_OBL_AND_DATES, NstClientCard.class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .setParameter("startDate1", Date.valueOf(startDate))
                    .setParameter("endDate1", Date.valueOf(endDate.plusDays(1)))
                    .setParameter("formatId", formatId)
                    .setParameter("oblId", oblId)
                    .getResultList();
        }
    }
}
