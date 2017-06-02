package com.photochecker.dao.mlka;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.User;
import com.photochecker.model.mlka.NkaResp;

import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public interface NkaRespDao extends GenericDao<NkaResp> {

    List<NkaResp> findAllByUser(User user);
}
