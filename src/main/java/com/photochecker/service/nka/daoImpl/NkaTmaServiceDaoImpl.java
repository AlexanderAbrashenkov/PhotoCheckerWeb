package com.photochecker.service.nka.daoImpl;

import com.photochecker.dao.common.FormatTypeDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.dao.nka.NkaParamDao;
import com.photochecker.dao.nka.NkaTmaDao;
import com.photochecker.model.common.FormatType;
import com.photochecker.model.common.Lka;
import com.photochecker.model.nka.NkaParam;
import com.photochecker.model.nka.NkaTma;
import com.photochecker.service.nka.NkaParamService;
import com.photochecker.service.nka.NkaTmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NkaTmaServiceDaoImpl implements NkaTmaService {

    @Autowired
    private LkaDao lkaDao;
    @Autowired
    private NkaTmaDao nkaTmaDao;

    @Override
    public List<NkaTma> getNkaTmaByDates(int nkaId, LocalDate startDate, LocalDate endDate, int formatId) {
        return nkaTmaDao.findAllByDates(nkaId, startDate, endDate, formatId);
    }

    @Override
    public Map<Integer, List<NkaTma>> getAllNkaTmaMap() {
        List<Lka> nkaList = lkaDao.findAllNka();
        List<NkaTma> nkaTmaList = nkaTmaDao.findAll();

        List<Integer> nkaIdWithTma = nkaTmaList.stream()
                .map(nkaTma -> nkaTma.getLka().getId())
                .collect(Collectors.toList());

        Map<Integer, List<NkaTma>> result = new HashMap<>();
        for (Lka nka : nkaList) {
            if (nkaIdWithTma.contains(nka.getId())) {
                List<NkaTma> nkaTmas = nkaTmaList.stream()
                        .filter(nkaTma -> nkaTma.getLka().getId() == nka.getId())
                        .collect(Collectors.toList());
                result.put(nka.getId(), nkaTmas);
            } else {
                result.put(nka.getId(), new ArrayList<>());
            }
        }

        return result;
    }

    @Override
    public int writeNewNkaTma(List<NkaTma> nkaTmaList) {
        Set<NkaTma> set = new HashSet<>(nkaTmaList);

        if (set.size() < nkaTmaList.size()) {
            System.out.println("There are duplicates in NkaTma to save");
            return 2;
        }

        nkaTmaDao.clearAll();
        for(NkaTma nkaTma : nkaTmaList) {
            nkaTmaDao.save(nkaTma);
        }

        return 1;
    }
}
