package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.EmployeeDao;
import com.photochecker.model.mlka.Employee;
import com.photochecker.service.common.EmployeeService;
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

    @Override
    public List<Employee> getEmployees(LocalDate startDate, LocalDate endDate, int repTypeInd) {
        return employeeDao.findAllByDates(startDate, endDate, repTypeInd);
    }
}
