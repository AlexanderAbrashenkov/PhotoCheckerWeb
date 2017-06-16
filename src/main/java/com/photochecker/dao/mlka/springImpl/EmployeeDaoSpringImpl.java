package com.photochecker.dao.mlka.springImpl;

import com.photochecker.dao.mlka.EmployeeDao;
import com.photochecker.model.mlka.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 31.05.2017.
 */
public class EmployeeDaoSpringImpl implements EmployeeDao {

    private final String SQL_FIND_BY_ID = "SELECT * FROM `employee_db` WHERE `emp_id` = ?";

    private final String SQL_FIND_ALL = "SELECT * FROM `employee_db`";

    private final String SQL_FIND_ALL_BY_DATES_AND_NKA = "select distinct emp.`emp_id`, emp.`name` from `employee_db` emp\n" +
            "inner join `photo_card` pc on pc.`employee_id` = emp.`emp_id`\n" +
            "inner join `client_card` cc on cc.`client_id` = pc.`client_id`\n" +
            "where pc.`date` >= ? and pc.`date` < ?\n" +
            "and cc.`distributor_id` = ?\n" +
            "and pc.`report_type` = ?\n" +
            "and cc.`nka_type` = ?\n" +
            "order by 2;";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public EmployeeDaoSpringImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .usingGeneratedKeyColumns("id")
            .withTableName("employee_db");
    }

    private RowMapper<Employee> employeeRowMapper = (rs, rowNum) -> {
        return new Employee(rs.getInt("emp_id"), rs.getString("name"));
    };

    @Override
    public int save(Employee employee) {
        Map<String, Object> params = new HashMap<>();
        params.put("emp_id", employee.getId());
        params.put("name", employee.getName());
        return simpleJdbcInsert.executeAndReturnKey(params).intValue();
    }

    @Override
    public Employee find(int id) {
        List<Employee> result = jdbcTemplate.query(SQL_FIND_BY_ID, employeeRowMapper, id);
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, employeeRowMapper);
    }

    @Override
    public boolean update(Employee employee) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public void remove(Employee employee) {
        throw new RuntimeException("This method not used");
    }

    @Override
    public List<Employee> findAllByDatesAndNka(int distrId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId) {
        endDate = endDate.plusDays(1);
        return jdbcTemplate.query(SQL_FIND_ALL_BY_DATES_AND_NKA, employeeRowMapper,
                Date.valueOf(startDate),
                Date.valueOf(endDate),
                distrId,
                repTypeInd,
                nkaId);
    }
}
