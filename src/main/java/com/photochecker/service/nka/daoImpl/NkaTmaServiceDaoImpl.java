package com.photochecker.service.nka.daoImpl;

import com.photochecker.dao.common.LkaDao;
import com.photochecker.dao.common.PhotoCardDao;
import com.photochecker.dao.common.ReportTypeDao;
import com.photochecker.dao.nka.NkaTmaDao;
import com.photochecker.model.common.Lka;
import com.photochecker.model.common.PhotoCard;
import com.photochecker.model.common.ReportType;
import com.photochecker.model.nka.NkaTma;
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
    @Autowired
    private PhotoCardDao photoCardDao;
    @Autowired
    private ReportTypeDao reportTypeDao;

    @Override
    public Map<String, List<NkaTma>> getAllNkaTmaMap() {
        List<Lka> nkaList = lkaDao.findAllNka();
        List<NkaTma> nkaTmaList = nkaTmaDao.findAll();

        List<String> nkaNameWithTma = nkaTmaList.stream()
                .map(nkaTma -> nkaTma.getLka().getName())
                .collect(Collectors.toList());

        Map<String, List<NkaTma>> result = new HashMap<>();
        for (Lka nka : nkaList) {
            if (nkaNameWithTma.contains(nka.getName())) {
                List<NkaTma> nkaTmas = nkaTmaList.stream()
                        .filter(nkaTma -> nkaTma.getLka().getId() == nka.getId())
                        .collect(Collectors.toList());
                result.put(nka.getName(), nkaTmas);
            } else {
                result.put(nka.getName(), new ArrayList<>());
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

    @Override
    public Map<String, Map<String, String>> getNkaTmaByDates(int nkaId, LocalDate startDate, LocalDate endDate, int formatId, int clientId) {
        //ReportType reportType = reportTypeDao.find(3);
        //List<PhotoCard> photoCardList = photoCardDao.findAllByRepClientDates(reportType, clientId, startDate, endDate);

       /* LocalDate firstPhotoDate = photoCardList.stream()
                .map(photoCard -> photoCard.getDate())
                .min(Comparator.naturalOrder())
                .get().toLocalDate();

        LocalDate lastPhotoDate = photoCardList.stream()
                .map(photoCard -> photoCard.getDate())
                .max(Comparator.naturalOrder())
                .get().toLocalDate();*/

        List<NkaTma> nkaTmaList = nkaTmaDao.findAllByNkaAndFormat(nkaId, formatId);
        nkaTmaList.removeIf(nkaTma ->
                !((
                        (nkaTma.getStartDate().isBefore(startDate) || nkaTma.getStartDate().isEqual(startDate))
                                &&
                                (nkaTma.getEndDate().isAfter(startDate) || nkaTma.getEndDate().isEqual(startDate))
                )
                        ||
                        (
                                (nkaTma.getStartDate().isBefore(endDate) || nkaTma.getStartDate().isEqual(endDate))
                                        &&
                                        (nkaTma.getEndDate().isAfter(endDate) || nkaTma.getEndDate().isEqual(endDate))
                        )
                ));

        List<String> tgNames = Arrays.asList("Майонез", "Кетчуп", "Соус");
        Map<String, Map<String, String>> result = new HashMap<>();

        for (String tg : tgNames) {
            List<NkaTma> nkaTmas = nkaTmaList.stream()
                    .filter(nkaTma -> nkaTma.getTgName().equals(tg))
                    .collect(Collectors.toList());
            Map<String, String> tgMap = new HashMap<>();
            int count = 0;
            String comment = "";

            if (nkaTmas.size() > 0) {
                for (NkaTma nkaTma : nkaTmas) {
                    count += nkaTma.getSkuCount();
                    comment += ("; " + nkaTma.getComment());
                }
                comment = comment.substring(2);
            }

            tgMap.put("count", Integer.toString(count));
            tgMap.put("comment", comment);

            result.put(tg, tgMap);
        }

        return result;
    }
}
