package com.photochecker.service.lkaMa;

import com.photochecker.model.lkaMa.LkaMaCriterias;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface LkaMaCriteriasService {
    public LkaMaCriterias getLkaCriterias(int lkaId);

    public List<LkaMaCriterias> getAllLkaCriterias();

    public boolean writeNewLkaCriterias(List<LkaMaCriterias> critList);
}
