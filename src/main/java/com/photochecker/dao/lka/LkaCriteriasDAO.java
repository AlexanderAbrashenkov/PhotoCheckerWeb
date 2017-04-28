package com.photochecker.dao.lka;

import com.photochecker.model.Lka;
import com.photochecker.model.lka.LkaCriterias;

import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface LkaCriteriasDAO {
    public boolean create(LkaCriterias lkaCriterias);
    public LkaCriterias find(Lka lka);
    public List<LkaCriterias> findAll();
    public boolean update(LkaCriterias lkaCriterias);
}
