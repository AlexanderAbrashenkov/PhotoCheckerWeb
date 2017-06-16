package com.photochecker.service.common.daoImpl;

import com.photochecker.dao.common.ChannelDao;
import com.photochecker.dao.common.DistrDao;
import com.photochecker.model.common.Channel;
import com.photochecker.model.common.Distr;
import com.photochecker.service.common.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
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
