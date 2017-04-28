package com.photochecker.mysqlDAO;

import com.photochecker.dao.DAOFactory;
import com.photochecker.dao.PhotoCardDAO;
import com.photochecker.model.DataSourcePhotochecker;
import com.photochecker.model.PhotoCard;
import com.photochecker.model.ReportType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public class PhotoCardDAOMySqlImpl implements PhotoCardDAO {
    @Override
    public boolean create(PhotoCard photoCard) {
        return false;
    }

    @Override
    public PhotoCard find(String url) {
        return null;
    }

    @Override
    public List<PhotoCard> findAll() {
        return null;
    }

    @Override
    public List<PhotoCard> findAllByDateClientRepType(ReportType reportType, int clientId, LocalDate startDate, LocalDate endDate) {
        List<PhotoCard> photoCardList = new ArrayList<>();
        endDate = endDate.plusDays(1);
        try {
            Connection connection = DAOFactoryMySqlImpl.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("select pc.`url`, pc.`date`, pc.`date_add`, pc.`comment`, pc.`checked`\n" +
                    "from `photo_card` pc\n" +
                    "inner join `client_card` cc on cc.`client_id` = pc.`client_id`\n" +
                    "where pc.`date` >= ? and pc.`date` < ?\n" +
                    "and cc.`client_id` = ?\n" +
                    "and pc.`report_type` = ?\n" +
                    "order by 2;");
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setInt(3, clientId);
            statement.setInt(4, reportType.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String comment = resultSet.getString("comment");
                if (comment == null || comment.equals("null")) comment = "";
                PhotoCard photoCard = new PhotoCard(clientId,
                        resultSet.getString("url"),
                        resultSet.getTimestamp("date").toLocalDateTime().toLocalDate(),
                        resultSet.getTimestamp("date_add").toLocalDateTime().toLocalDate(),
                        comment,
                        resultSet.getBoolean("checked"),
                        reportType);
                photoCardList.add(photoCard);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoCardList;
    }
}
