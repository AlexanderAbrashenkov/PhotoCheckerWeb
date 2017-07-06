package com.photochecker.service.nka;

import com.photochecker.model.nka.NkaParam;

import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface NkaParamService {
    public NkaParam getNkaParam(int nkaId);

    public List<NkaParam> getAllNkaParams();

    public boolean writeNewNkaParam(List<NkaParam> critList);
}
