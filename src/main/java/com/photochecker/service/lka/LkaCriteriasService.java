package com.photochecker.service.lka;


import com.photochecker.model.lka.LkaCriterias;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface LkaCriteriasService {
    public LkaCriterias getLkaCriterias(int lkaId);

    public List<LkaCriterias> getAllLkaCriterias();

    public boolean writeNewLkaCriterias(List<LkaCriterias> critList);
}
