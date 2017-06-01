package com.photochecker.service.serviceDaoImpl.mlka;

import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.mlka.NkaRespService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
public class NkaRespServiceDaoImpl implements NkaRespService {

    @Autowired
    private NkaRespDao nkaRespDao;

    @Override
    public List<NkaResp> getAllNkaResp() {
        List<NkaResp> nkaRespList = nkaRespDao.findAll();
        Collections.sort(nkaRespList);
        return nkaRespList;
    }

    @Override
    public boolean writeNkaResp (List<NkaResp> respList) {
        for (NkaResp nkaResp : respList) {
            nkaRespDao.update(nkaResp);
        }
        return true;
    }
}
