package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstResp;

import java.util.List;

/**
 * Created by market6 on 21.06.2017.
 */
public interface NstRespDao extends GenericDao<NstResp> {
    List<NstResp> findAllByUser(User user);
}
