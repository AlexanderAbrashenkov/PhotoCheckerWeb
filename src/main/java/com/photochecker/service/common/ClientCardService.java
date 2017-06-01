package com.photochecker.service.common;

import com.photochecker.model.ClientCard;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface ClientCardService {

    public List<ClientCard> getClientCardList(int distrId, int lkaId, int channelId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);

    List<ClientCard> getClientCardList(int distrId, int mlkaId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd, int nkaId);
}
