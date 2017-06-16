package com.photochecker.dao.common.springImpl;

import com.photochecker.dao.common.ChannelDao;
import com.photochecker.model.common.Channel;
import com.photochecker.model.common.Distr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 25.05.2017.
 */
public class ChannelDaoSpringImpl implements ChannelDao {

    private String SQL_SAVE = "INSERT INTO `channel_db` (`id`, `name`) VALUES (?, ?)";

    private String SQL_FIND_BY_ID = "SELECT * FROM `channel_db` WHERE `id` = ?";

    private String SQL_FIND_ALL = "SELECT * FROM `channel_db`";

    private String SQL_FIND_ALL_BY_PARAMS = "select distinct c.`id`, c.`name`\n" +
            "from `channel_db` c\n" +
            "inner join `client_card` cc on cc.`channel_id` = c.`id`\n" +
            "inner join `photo_card` pc on pc.`client_id` = cc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`region_id` = ?\n" +
            "and cc.`distributor_id` = ?\n" +
            "and pc.`report_type` = ?\n" +
            "order by 1;";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ChannelDaoSpringImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Channel> channelRowMapper = (rs, rowNum) -> {
        return new Channel(rs.getInt("id"),
                rs.getString("name"));
    };

    @Override
    public int save(Channel channel) {
        return jdbcTemplate.update(SQL_SAVE,
                channel.getId(),
                channel.getName());
    }

    @Override
    public Channel find(int id) {
        List<Channel> result = jdbcTemplate.query(SQL_FIND_BY_ID, channelRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Channel> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, channelRowMapper);
    }

    @Override
    public boolean update(Channel channel) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(Channel channel) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<Channel> findAllByDistrAndDates(Distr distr, LocalDate startDate, LocalDate endDate, int repTypeInd) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_ALL_BY_PARAMS, channelRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                distr.getRegion().getId(),
                distr.getId(),
                repTypeInd);
    }
}
