package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.ChannelDao;
import com.photochecker.dao.common.ClientCardDao;
import com.photochecker.dao.common.LkaDao;
import com.photochecker.model.common.Channel;
import com.photochecker.model.common.ClientCard;
import com.photochecker.model.common.Lka;
import com.photochecker.service.common.ClientCardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ClientCardServiceDaoImpl implements ClientCardService {
    @Autowired
    private LkaDao lkaDao;
    @Autowired
    private ClientCardDao clientCardDao;
    @Autowired
    private ChannelDao channelDao;

    @Override
    public List<ClientCard> getClientCardList(int distrId, int lkaId, int channelId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd) {

        List<ClientCard> clientCardList;
        if (channelId == 1) {
            Lka lka = lkaDao.find(lkaId);
            clientCardList = clientCardDao.findAllByLkaAndDates(lka, dateFrom, dateTo, repTypeInd);
        } else {
            Channel channel = channelDao.find(channelId);
            clientCardList = clientCardDao.findAllByChannelAndDates(channel, dateFrom, dateTo, repTypeInd);
        }

        clientCardList.removeIf(clientCard -> clientCard.getDistr().getId() != distrId);

        return clientCardList;
    }

    @Override
    public List<ClientCard> getClientCardList(int distrId, int mlkaId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd, int nkaId) {
        return clientCardDao.findAllByNkaAndDates(mlkaId, dateFrom, dateTo, repTypeInd, nkaId, distrId);
    }
}
