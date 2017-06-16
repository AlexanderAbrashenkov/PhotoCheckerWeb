package com.photochecker.service.mlka.daoImpl;

import com.photochecker.dao.mlka.EmployeeDao;
import com.photochecker.model.mlka.Employee;
import com.photochecker.service.mlka.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmployeeServiceDaoImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> getEmployees(int distrId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId) {
        return employeeDao.findAllByDatesAndNka(distrId, startDate, endDate, repTypeInd, nkaId);
    }
}
