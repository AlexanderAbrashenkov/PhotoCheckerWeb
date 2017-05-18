package com.photochecker.service.lka;

import com.photochecker.model.Distr;
import com.photochecker.model.Responsibility;
import com.photochecker.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by market6 on 17.05.2017.
 */
public interface DistrService {


    public List<Distr> getDistrs(User user, int regionId, LocalDate dateFrom, LocalDate dateTo);
}
