package com.photochecker.service.common;

import com.photochecker.model.common.Channel;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 25.05.2017.
 */
public interface ChannelService {
    List<Channel> getChannels(int distrId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);
}
