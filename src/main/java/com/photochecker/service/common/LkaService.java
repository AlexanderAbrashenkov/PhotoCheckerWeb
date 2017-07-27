package com.photochecker.service.common;

import com.photochecker.model.common.Lka;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by market6 on 17.05.2017.
 */
public interface LkaService {
    public List<Lka> getLkas(int distrId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);

    public Lka getLkaById(int id);

    List<Lka> getLkaByRjkam(LocalDate dateFrom, LocalDate dateTo, int repTypeIndex, int rjkamId);

    Map<String,Integer> getAllNkaMap();
}
