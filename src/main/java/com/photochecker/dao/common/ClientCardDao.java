package com.photochecker.dao.common;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.common.Channel;
import com.photochecker.model.common.ClientCard;
import com.photochecker.model.common.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCardDao extends GenericDao<ClientCard> {

    List<ClientCard> findAllByLkaAndDates(Lka lka, LocalDate startDate, LocalDate endDate, int repTypeInd);

    List<ClientCard> findAllByChannelAndDates(Channel channel, LocalDate startDate, LocalDate endDate, int repTypeInd);

    List<ClientCard> findAllByNkaAndDates(int mlkaId, LocalDate startDate, LocalDate endDate, int repTypeInd, int nkaId, int distrId);
}
