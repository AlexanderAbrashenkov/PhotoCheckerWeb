package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.DaoFactory;
import com.photochecker.dao.common.ClientCardDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.ClientCard;
import com.photochecker.model.Lka;
import com.photochecker.service.lka.ClientCardService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ClientCardServiceDaoImpl implements ClientCardService {
    private LkaDao lkaDao;
    private ClientCardDao clientCardDao;

    public ClientCardServiceDaoImpl() {
        super();
        lkaDao = DaoFactory.getLkaDAO();
        clientCardDao = DaoFactory.getClientCardDAO();
    }

    @Override
    public List<ClientCard> getClientCardList(int distrId, int lkaId, LocalDate dateFrom, LocalDate dateTo) {
        Lka lka = null;
        lka = lkaDao.find(lkaId);
        List<ClientCard> clientCardList = clientCardDao.findAllByLkaAndDates(lka, dateFrom, dateTo);

        clientCardList.removeIf(clientCard -> clientCard.getDistr().getId() != distrId);
        clientCardList.removeIf(clientCard -> clientCard.getLka().getId() != lkaId);

        return clientCardList;
    }
}
