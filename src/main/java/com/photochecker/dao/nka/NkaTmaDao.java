package com.photochecker.dao.nka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nka.NkaParam;
import com.photochecker.model.nka.NkaTma;

import java.time.LocalDate;
import java.util.List;


public interface NkaTmaDao extends GenericDao<NkaTma> {

    void clearAll();

    List<NkaTma> findAllByNkaAndFormat(int nkaId, int formatId);
}
