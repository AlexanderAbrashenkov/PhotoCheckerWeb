package com.photochecker.service.nst.daoImpl;

import com.photochecker.dao.nst.NstPhotoCardDao;
import com.photochecker.model.nst.NstPhotoCard;
import com.photochecker.service.nst.NstPhotoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NstPhotoCardServiceDaoImpl implements NstPhotoCardService {
    @Autowired
    private NstPhotoCardDao nstPhotoCardDao;

    @Override
    public List<NstPhotoCard> getPhotoListNst(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        List<NstPhotoCard> photoCardList = nstPhotoCardDao.findAllByDatesNst(clientId, dateFrom, dateTo);

        return photoCardList;
    }
}
