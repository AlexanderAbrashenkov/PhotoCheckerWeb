package com.photochecker.service.nka.daoImpl;

import com.photochecker.dao.common.LkaDao;
import com.photochecker.dao.nka.NkaParamDao;
import com.photochecker.model.common.Lka;
import com.photochecker.model.nka.NkaParam;
import com.photochecker.service.nka.NkaParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NkaParamServiceDaoImpl implements NkaParamService {

    @Autowired
    private NkaParamDao nkaParamDao;
    @Autowired
    private LkaDao lkaDao;

    @Override
    public NkaParam getNkaParam(int nkaId) {
        NkaParam nkaParam = nkaParamDao.find(nkaId);
        return nkaParam;
    }

    @Override
    public List<NkaParam> getAllNkaParams() {
        List<Lka> nkaList = lkaDao.findAllNka();
        List<NkaParam> nkaParamList = nkaParamDao.findAll();

        List<Integer> nkaIdWithParam = nkaParamList.stream()
                .map(nkaParam -> nkaParam.getNkaId())
                .collect(Collectors.toList());
        for (Lka nka : nkaList) {
            if (!nkaIdWithParam.contains(nka.getId())) {
                nkaParamList.add(new NkaParam(nka.getId(), nka.getName()));
                nkaIdWithParam.add(nka.getId());
            }
        }

        return nkaParamList;
    }

    @Override
    public boolean writeNewNkaParam(List<NkaParam> critList) {
        boolean succeed;
        List<NkaParam> savedParamList = nkaParamDao.findAll();
        int id = -1;
        for (NkaParam nkaParam : critList) {
            if (savedParamList.contains(nkaParam)) {
                nkaParamDao.update(nkaParam);
            } else {
                id = nkaParamDao.save(nkaParam);
            }
        }

        for (NkaParam nkaParam : savedParamList) {
            if (!critList.contains(nkaParam)) {
                nkaParamDao.remove(nkaParam);
            }
        }

        succeed = true;
        return succeed;
    }
}
