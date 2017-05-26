package com.photochecker.service.common;

import com.photochecker.model.Distr;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface LkaService {
    public List<Lka> getLkas(int distrId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);

    public Lka getLkaById(int id);
}
