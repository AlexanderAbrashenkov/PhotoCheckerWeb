package com.photochecker.service.nka;

import com.photochecker.model.nka.NkaParam;
import com.photochecker.model.nka.NkaTma;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface NkaTmaService {

    Map<String, Map<String, String>> getNkaTmaByDates(int nkaId, LocalDate startDate, LocalDate endDate, int formatId, int clientId);

    public Map<String, List<NkaTma>> getAllNkaTmaMap();

    public int writeNewNkaTma(List<NkaTma> nkaTmaList);
}
