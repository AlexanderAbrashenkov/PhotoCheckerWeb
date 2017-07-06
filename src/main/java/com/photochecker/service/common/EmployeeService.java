package com.photochecker.service.common;

import com.photochecker.model.mlka.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 31.05.2017.
 */
public interface EmployeeService {
    List<Employee> getEmployees(int distrId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId);

    List<Employee> getEmployees(LocalDate startDate, LocalDate endDate, int repTypeInd);
}
