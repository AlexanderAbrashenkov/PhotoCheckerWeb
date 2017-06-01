package com.photochecker.service.mlka;

import com.photochecker.model.mlka.NkaResp;

import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public interface NkaRespService {

    List<NkaResp> getAllNkaResp();

    boolean writeNkaResp(List<NkaResp> respList);
}
