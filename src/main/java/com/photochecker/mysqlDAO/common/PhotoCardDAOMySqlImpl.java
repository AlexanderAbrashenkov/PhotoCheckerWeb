package com.photochecker.mysqlDAO.common;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.common.PhotoCardDAO;
import com.photochecker.model.PersistException;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;
import com.photochecker.mysqlDAO.AbstractDAOMySqlImpl;
import com.photochecker.mysqlDAO.DAOFactoryMySqlImpl;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class PhotoCardDAOMySqlImpl extends AbstractDAOMySqlImpl<PhotoCard> implements PhotoCardDAO {

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getFindQuery() {
        return null;
    }

    @Override
    public String getFindAllQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getFindAllByParametersQuery() {
        return "select pc.`url`, pc.`date`, pc.`date_add`, pc.`comment`, pc.`checked`, pc.`client_id`, pc.`report_type`\n" +
                "from `photo_card` pc\n" +
                "inner join `client_card` cc on cc.`client_id` = pc.`client_id`\n" +
                "where pc.`date` >= ? and pc.`date` < ?\n" +
                "and cc.`client_id` = ?\n" +
                "and pc.`report_type` = ?\n" +
                "order by 2;";
    }

    @Override
    protected List<PhotoCard> parseResultSet(ResultSet resultSet) throws SQLException, PersistException {
        List<ReportType> reportTypeList = DAOFactory.getDAOFactory().getReportTypeDAO().findAll();
        List<PhotoCard> photoCardList = new ArrayList<>();
        while (resultSet.next()) {
            String comment = resultSet.getString("comment");
            if (comment == null || comment.equals("null")) comment = "";

            int reportId = resultSet.getInt("report_type");
            ReportType reportType = reportTypeList.stream()
                    .filter(reportType1 -> reportType1.getId() == reportId)
                    .findFirst()
                    .get();

            PhotoCard photoCard = new PhotoCard(
                    resultSet.getInt("client_id"),
                    resultSet.getString("url"),
                    resultSet.getTimestamp("date").toLocalDateTime().toLocalDate(),
                    resultSet.getTimestamp("date_add").toLocalDateTime().toLocalDate(),
                    comment,
                    resultSet.getBoolean("checked"),
                    reportType);

            photoCardList.add(photoCard);
        }
        return photoCardList;
    }

    @Override
    protected void prepareStatementForCreate(PreparedStatement statement, PhotoCard object) {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, PhotoCard object) {

    }

    @Override
    protected void prepareStatementForFindAllByParameters(PreparedStatement statement, Object[] params) throws SQLException {
        ReportType reportType = (ReportType) params[0];
        int clientId = (int) params[1];
        LocalDate startDate = (LocalDate) params[2];
        LocalDate endDate = (LocalDate) params[3];
        endDate = endDate.plusDays(1);

        statement.setDate(1, Date.valueOf(startDate));
        statement.setDate(2, Date.valueOf(endDate));
        statement.setInt(3, clientId);
        statement.setInt(4, reportType.getId());
    }
}
