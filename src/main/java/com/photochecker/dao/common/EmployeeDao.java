package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.mlka.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 31.05.2017.
 */
public interface EmployeeDao extends GenericDao<Employee> {
    List<Employee> findAllByDatesAndNka(int distrId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId);

    List<Employee> findAllByDates(LocalDate startDate, LocalDate endDate, int repTypeInd);
}
