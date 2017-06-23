package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstRespDao;
import com.photochecker.model.nst.NstResp;
import com.photochecker.service.nst.NstRespService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class NstRespServiceDaoImpl implements NstRespService {

    @Autowired
    private NstRespDao nstRespDao;

    @Override
    public List<NstResp> getAllNstResp() {
        List<NstResp> nstRespList = nstRespDao.findAll();
        Collections.sort(nstRespList);
        return nstRespList;
    }

    @Override
    public boolean writeNstResp(List<NstResp> nstRespList) {
        for (NstResp nstResp : nstRespList) {
            nstRespDao.update(nstResp);
        }
        return true;
    }
}
