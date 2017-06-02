package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.model.common.PhotoCard;
import com.photochecker.model.common.ReportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 18.05.2017.
 */
public class PhotoCardDaoSpringImpl implements PhotoCardDao {

    private JdbcTemplate jdbcTemplate;
    private List<ReportType> reportTypeList;

    @Autowired
    private ReportTypeDao reportTypeDao;

    private final String SQL_FIND_BY_PARAMS = "select *\n" +
            "from `photo_card` pc\n" +
            "inner join `client_card` cc on cc.`client_id` = pc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`client_id` = ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 2;";

    private final String SQL_FIND_BY_DATES_ADD = "select *\n" +
            "from `photo_card` pc\n" +
            "where pc.`date_add` >= ? and pc.`date_add` < ?";

    private final String SQL_SAVE = "INSERT INTO `photo_card`\n" +
            "(`client_id`, `url`, `date`, `date_add`, `comment`, `report_type`, `employee_id`)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private final String SQL_FIND_BY_URL = "select * from `photo_card` where `url` = ? and `report_type` = ? limit 1";

    @Autowired
    public PhotoCardDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void setPhotoCardFields() {
        reportTypeList = reportTypeDao.findAll();
    }

    private RowMapper<PhotoCard> photoCardRowMapper = (resultSet, i) -> {
        String comment = resultSet.getString("comment");
        if (comment == null || comment.equals("null")) comment = "";

        int reportId = resultSet.getInt("report_type");
        ReportType reportType = reportTypeList.stream()
                .filter(reportType1 -> reportType1.getId() == reportId)
                .findFirst()
                .get();

        return new PhotoCard(
                resultSet.getInt("client_id"),
                resultSet.getString("url"),
                resultSet.getTimestamp("date").toLocalDateTime(),
                resultSet.getTimestamp("date_add").toLocalDateTime(),
                comment,
                resultSet.getBoolean("checked"),
                reportType,
                resultSet.getInt("employee_id"));
    };

    @Override
    public int save(PhotoCard photoCard) {
        return jdbcTemplate.update(SQL_SAVE,
                photoCard.getClientId(),
                photoCard.getUrl(),
                Timestamp.valueOf(photoCard.getDate()),
                Timestamp.valueOf(photoCard.getDateAdd()),
                photoCard.getComment(),
                photoCard.getReportType().getId(),
                photoCard.getEmployeeId());
    }

    @Override
    public PhotoCard find(int id) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<PhotoCard> findAll() {
        throw new RuntimeException("This method not used");
    }

    @Override
    public boolean update(PhotoCard photoCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(PhotoCard photoCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<PhotoCard> findAllByRepClientDates(ReportType reportType, int clientId, LocalDate startDate, LocalDate endDate) {
        setPhotoCardFields();
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, photoCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                clientId,
                reportType.getId());
    }

    @Override
    public List<PhotoCard> findAllByDates(LocalDate startDate, LocalDate endDate) {
        setPhotoCardFields();
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_DATES_ADD, photoCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate));
    }

    @Override
    public PhotoCard findByUrl(String url, ReportType reportType) {
        setPhotoCardFields();
        List<PhotoCard> result = jdbcTemplate.query(SQL_FIND_BY_URL, photoCardRowMapper, url, reportType.getId());
        return result.size() > 0 ? result.get(0) : null;
    }
}
