package com.photochecker.service.nst;

import com.photochecker.model.nst.NstResp;

import java.util.List;

/**
 * Created by market6 on 22.06.2017.
 */
public interface NstRespService {
    List<NstResp> getAllNstResp();

    boolean writeNstResp(List<NstResp> nstRespList);
}
