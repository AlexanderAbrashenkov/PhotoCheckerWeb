package com.photochecker.service.serviceDaoImpl.common;

import com.photochecker.dao.common.ChannelDao;
import com.photochecker.dao.common.DistrDao;
import com.photochecker.model.Channel;
import com.photochecker.model.Distr;
import com.photochecker.service.common.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 25.05.2017.
 */
public class ChannelServiceDaoImpl implements ChannelService {

    @Autowired
    private ChannelDao channelDao;
    @Autowired
    private DistrDao distrDao;

    @Override
    public List<Channel> getChannels(int distrId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd) {
        Distr distr = distrDao.find(distrId);
        return channelDao.findAllByDistrAndDates(distr, dateFrom, dateTo, repTypeInd);
    }
}
