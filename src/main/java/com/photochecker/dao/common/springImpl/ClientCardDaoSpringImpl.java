package com.photochecker.dao.common.springImpl;

import com.photochecker.dao.common.*;
import com.photochecker.model.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientCardDaoSpringImpl implements ClientCardDao {

    //language=SQL
    private String SQL_FIND_BY_LKA = "select distinct cc.*, \n" +
            "case when sav.`save_date` is not null then 1 else 0 end checked\n" +
            "from `client_card` cc\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "left join (select slka.`client_id`, slka.`save_date` from %s slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`lka_id` = ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 2;";

    //language=SQL
    private String SQL_FIND_BY_NKA = "select distinct cc.*, \n" +
            "case when sav.`save_date` is not null then 1 else 0 end checked\n" +
            "from `client_card` cc\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "left join (select slka.`client_id`, slka.`save_date` from `save_mlka_db` slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and pc.`report_type` = ?\n" +
            "and cc.`nka_type` = ?\n" +
            "and pc.`employee_id` = ?\n" +
            "and cc.`distributor_id` = ?\n" +
            "order by 2;";

    //language=SQL
    private String SQL_FIND_BY_CHANNEL = "select distinct cc.*, \n" +
            "case when sav.`save_date` is not null then 1 else 0 end checked\n" +
            "from `client_card` cc\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "left join (select slka.`client_id`, slka.`save_date` from %s slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`channel_id` = ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 2;";

    private String SQL_FIND_BY_RJKAM = "select distinct cc.*, \n" +
            "case when sav.`save_date` is not null then 1 else 0 end checked\n" +
            "from `client_card` cc\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "left join (select slka.`client_id`, slka.`save_date` from save_nka_db slka where slka.date_from=? and slka.date_to=?) sav on sav.client_id=cc.client_id\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and pc.`report_type` = ?\n" +
            "and pc.`employee_id` = ?\n" +
            "and cc.lka_id = ?\n" +
            "order by 2;";

    private final String SQL_FIND_BY_ID = "select *, 0 as checked " +
            "from `client_card` cc " +
            "where `client_id`=? " +
            "order by 2";

    private final String SQL_FIND_ALL = "select *, 0 as checked " +
            "from `client_card` cc " +
            "order by 2";

    private final String SQL_SAVE = "INSERT INTO `client_card`\n" +
            "(`client_id`, `client_name`, `client_address`, `region_id`, " +
            "`obl`, `distributor_id`, `channel_id`, `lka_id`, `format_id`, `nka_type`)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final String SQL_UPDATE = "UPDATE `client_card`\n" +
            "SET `client_name` = ?, `client_address` = ?, `channel_id` = ?, `lka_id` = ?, `format_id` = ?, `nka_type` = ?\n" +
            "WHERE `client_id` = ?";


    private JdbcTemplate jdbcTemplate;
    private List<Distr> distrList;
    private List<Lka> lkaList;
    private List<PhotoCard> photoCardList;
    private List<FormatType> formatTypeList;

    @Autowired
    private DistrDao distrDao;
    @Autowired
    private LkaDao lkaDao;
    @Autowired
    private PhotoCardDao photoCardDao;
    @Autowired
    private FormatTypeDao formatTypeDao;

    private RowMapper<ClientCard> clientCardRowMapper = (resultSet, i) -> {

        int distrId = resultSet.getInt("distributor_id");
        Distr distr = null;
        if (distrId != 0) {
            distr = distrList.stream()
                    .filter(distr1 -> distr1.getId() == distrId)
                    .findFirst()
                    .get();
        }

        int lkaId = resultSet.getInt("lka_id");
        Lka lka = null;
        if (lkaId != 0) {
            lka = lkaList.stream()
                    .filter(lka1 -> lka1.getId() == lkaId)
                    .findFirst()
                    .get();
        }

        int formatId = resultSet.getInt("format_id");
        FormatType formatType = null;
        if (formatId != 0) {
            formatType = formatTypeList.stream()
                    .filter(formatType1 -> formatType1.getId() == formatId)
                    .findFirst()
                    .get();
        }

        int clientId = resultSet.getInt("client_id");

        ClientCard clientCard = new ClientCard(
                clientId,
                resultSet.getString("client_name"),
                resultSet.getString("client_address"),
                formatType,
                resultSet.getInt("checked"),
                distr,
                resultSet.getString("obl"),
                resultSet.getInt("channel_id"),
                lka,
                resultSet.getInt("nka_type")
        );

        if (clientCard.getChecked() == 1) {
            List<PhotoCard> notSavedPhoto = photoCardList.stream()
                    .filter(photoCard -> photoCard.getClientId() == clientId)
                    .filter(photoCard -> !photoCard.isChecked())
                    .collect(Collectors.toList());
            if (notSavedPhoto.size() > 0) {
                clientCard.setChecked(2);
            }
        }

        return clientCard;
    };

    @Autowired
    public ClientCardDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private void setClientCardFields(LocalDate startDate, LocalDate endDate, int repTypeInd) {
        distrList = distrDao.findAll();
        lkaList = lkaDao.findAll();
        if (startDate != null && endDate != null) {
            photoCardList = photoCardDao.findAllByDates(startDate, endDate);
            photoCardList.removeIf(photoCard -> photoCard.getReportType().getId() != repTypeInd);
        }
        formatTypeList = formatTypeDao.findAll();
    }

    private String getDbName(int repTypeInd) {
        String dbName = "";
        switch (repTypeInd) {
            case 1: dbName = "save_lka_dmp_db";
                break;
            case 5: dbName = "save_lka_db";
                break;
        }
        return dbName;
    }

    @Override
    public int save(ClientCard clientCard) {
        return jdbcTemplate.update(SQL_SAVE,
                clientCard.getClientId(),
                clientCard.getClientName(),
                clientCard.getClientAddress(),
                clientCard.getDistr() != null ? clientCard.getDistr().getRegion().getId() : 0,
                clientCard.getObl(),
                clientCard.getDistr() != null ? clientCard.getDistr().getId() : 0,
                clientCard.getChannelId(),
                clientCard.getLka() != null ? clientCard.getLka().getId() : 0,
                clientCard.getFormatType().getId(),
                clientCard.getNkaType());
    }

    @Override
    public ClientCard find(int id) {
        setClientCardFields(null, null, 0);
        List<ClientCard> result = jdbcTemplate.query(SQL_FIND_BY_ID, clientCardRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<ClientCard> findAll() {
        setClientCardFields(null, null, 0);
        return jdbcTemplate.query(SQL_FIND_ALL, clientCardRowMapper);
    }

    @Override
    public boolean update(ClientCard clientCard) {
        jdbcTemplate.update(SQL_UPDATE,
                clientCard.getClientName(),
                clientCard.getClientAddress(),
                clientCard.getChannelId(),
                clientCard.getLka() != null ? clientCard.getLka().getId() : 0,
                clientCard.getFormatType().getId(),
                clientCard.getNkaType(),
                clientCard.getClientId());
        return true;
    }

    @Override
    public void remove(ClientCard clientCard) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<ClientCard> findAllByLkaAndDates(Lka lka, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        return findAllByParams(startDate, endDate, repTypeInd, lka, null);
    }

    @Override
    public List<ClientCard> findAllByChannelAndDates(Channel channel, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        return findAllByParams(startDate, endDate, repTypeInd, null, channel);
    }

    @Override
    public List<ClientCard> findAllByNkaAndDates(int mlkaId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId, int distrId) {
        setClientCardFields(startDate, endDate, repTypeInd);
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_NKA, clientCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate.minusDays(1)),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repTypeInd,
                nkaId,
                mlkaId,
                distrId);
    }

    @Override
    public List<ClientCard> findAllByRjkamAndDates(int rjkamId, int nkaId, LocalDate startDate, LocalDate endDate, int repTypeIndex) {
        setClientCardFields(startDate, endDate, repTypeIndex);
        endDate = endDate.plusDays(1);

        return jdbcTemplate.query(SQL_FIND_BY_RJKAM, clientCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate.minusDays(1)),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                repTypeIndex,
                rjkamId,
                nkaId);
    }

    private List<ClientCard> findAllByParams(LocalDate startDate, LocalDate endDate, int repTypeInd, Lka lka, Channel channel) {
        setClientCardFields(startDate, endDate, repTypeInd);
        endDate = endDate.plusDays(1);

        String dbName = getDbName(repTypeInd);

        int id = 0;
        String sql = null;
        if (lka != null) {
            id = lka.getId();
            sql = String.format(SQL_FIND_BY_LKA, dbName);
        } else if (channel != null) {
            id = channel.getId();
            sql = String.format(SQL_FIND_BY_CHANNEL, dbName);
        }

        return jdbcTemplate.query(sql, clientCardRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate.minusDays(1)),
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                id,
                repTypeInd);
    }
}
