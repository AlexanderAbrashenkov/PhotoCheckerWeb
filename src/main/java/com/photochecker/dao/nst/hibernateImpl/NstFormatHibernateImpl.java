package com.photochecker.dao.nst.hibernateImpl;

import com.photochecker.dao.nst.NstFormatDao;
import com.photochecker.model.nst.NstFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

@Repository
public class NstFormatHibernateImpl implements NstFormatDao {

    //language=SQL
    /*private final String SQL_FIND_BY_DATES = "SELECT DISTINCT f.`name`, f.`id`\n" +
            "FROM `nst_format` f\n" +
            "INNER JOIN `nst_client_card` cc ON cc.`format_id` = f.`id`\n" +
            "INNER JOIN `nst_photo` pc ON pc.`client_id` = cc.`id`\n" +
            "WHERE pc.`date` >= :startDate AND pc.`date` < :endDate\n" +
            "ORDER BY 1;";*/

    //language=SQL
    private final String SQL_FIND_BY_DATES = "SELECT DISTINCT f " +
            "FROM NstFormat f " +
            "JOIN NstClientCard cc  " +
            "JOIN NstPhotoCard pc " +
            "WHERE pc.date >= :startDate AND pc.date < :endDate " +
            "ORDER BY f.id";

    //language=SQL
    /*private final String SQL_FIND_ALL_SIMPLE = "SELECT DISTINCT f.`name`, f.`id`\n" +
            "FROM `nst_format` f\n" +
            "INNER JOIN `nst_client_card` cc ON cc.`format_id` = f.`id`\n" +
            "INNER JOIN %s pc ON pc.`client_id` = cc.`id`\n" +
            "ORDER BY 1;";*/

    //language=SQL
    private final String SQL_FIND_ALL_SIMPLE = "SELECT DISTINCT f " +
            "FROM NstFormat f " +
            "JOIN NstClientCard cc " +
            "JOIN %s pc " +
            "ORDER BY f.id";

    @Autowired
    private Properties properties;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public NstFormatHibernateImpl(DataSource dataSource) {
    }

    @Override
    public int save(NstFormat nstFormat) {
        throw new RuntimeException("This method not used");
    }

    @Override
    @Transactional
    public NstFormat find(int id) {
        return entityManager.find(NstFormat.class, id);
    }

    @Override
    @Transactional
    public List<NstFormat> findAll() {
        return entityManager.createQuery("select f from NstFormat f", NstFormat.class)
                .getResultList();
    }

    @Override
    public boolean update(NstFormat nstFormat) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(NstFormat nstFormat) {
        throw new RuntimeException("This method not used");
    }

    @Override
    @Transactional
    public List<NstFormat> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String photoTableName = startDate.format(formatter) + "_" + endDate.format(formatter) + "_nst_photo";

        if (photoTableName.equals(properties.getProperty("nst.current.week.photo"))
                || photoTableName.equals(properties.getProperty("nst.prev.week.photo"))) {
            String sql = String.format(SQL_FIND_ALL_SIMPLE, photoTableName);
            return entityManager.createQuery(sql, NstFormat.class).getResultList();
        } else {
            return entityManager.createQuery(SQL_FIND_BY_DATES, NstFormat.class)
                    .setParameter("startDate", Date.valueOf(startDate))
                    .setParameter("endDate", Date.valueOf(endDate))
                    .getResultList();
        }
    }
}
