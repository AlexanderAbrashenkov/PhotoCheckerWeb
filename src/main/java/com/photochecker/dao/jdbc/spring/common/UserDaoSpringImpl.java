package com.photochecker.dao.jdbc.spring.common;

import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.common.UserDao;
import com.photochecker.model.ReportType;
import com.photochecker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 18.05.2017.
 */
public class UserDaoSpringImpl implements UserDao {

    private final String SQL_FIND_BY_ID = "SELECT * FROM `users`\n" +
            "WHERE `id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `users`";

    private final String SQL_FIND_BY_PARAMS = "SELECT * FROM `users` " +
            "WHERE `user_login` = ? ";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReportTypeDao reportTypeDao;

    private RowMapper<User> userRowMapper = (resultSet, i) -> {
        int id = resultSet.getInt("id");
        String login = resultSet.getString("user_login");
        String userName = resultSet.getString("user_name");

        int userRole = resultSet.getInt("user_role");

        User user = new User(id, login, userName, userRole, null);

        /*ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        List<ReportType> reportTypeList = ((ReportTypeDao) context.getBean("reportTypeDao")).findAllByUser(user);*/
        List<ReportType> reportTypeList = reportTypeDao.findAllByUser(user);

        user.setReportTypeList(reportTypeList);

        return user;
    };

    @Autowired
    public UserDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(User user) {
        return 0;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public void remove(User user) {

    }

    @Override
    public List<User> findAllByLogin(String login) {
        return jdbcTemplate.query(SQL_FIND_BY_PARAMS, userRowMapper, login);
    }

    @Override
    public User checkLoginAndPassword(String login, String password) {

        Map<String, Object> userRow = jdbcTemplate.queryForMap(SQL_FIND_BY_PARAMS, login);
        User user = findAllByLogin(login).get(0);

        int id = (int) userRow.get("id");
        String userName = (String) userRow.get("user_name");
        String userPass = (String) userRow.get("user_pass");
        String userSalt = (String) userRow.get("salt");

        String passSalt = password + userSalt;
        StringBuffer code = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte bytes[] = passSalt.getBytes();
            byte digest[] = messageDigest.digest(bytes); //save code
            for (int i = 0; i < digest.length; ++i) {
                code.append(Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (!code.toString().equals(userPass)) {
            return null;
        }

        return user;
    }
}
