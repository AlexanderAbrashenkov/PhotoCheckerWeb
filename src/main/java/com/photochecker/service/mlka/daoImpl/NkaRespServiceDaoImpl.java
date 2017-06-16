package com.photochecker.service.mlka.daoImpl;

import com.photochecker.dao.mlka.NkaRespDao;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.mlka.NkaRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
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
