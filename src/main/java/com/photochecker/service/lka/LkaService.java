package com.photochecker.service.lka;

import com.photochecker.model.Distr;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface LkaService {
    public List<Lka> getLkas(int distrId, LocalDate dateFrom, LocalDate dateTo);

    public Lka getLkaById(int id);
}
