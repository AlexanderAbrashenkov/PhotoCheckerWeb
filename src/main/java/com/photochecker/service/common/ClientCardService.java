package com.photochecker.service.common;

import com.photochecker.model.common.ClientCard;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface ClientCardService {

    public List<ClientCard> getClientCardList(int distrId, int lkaId, int channelId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);

    List<ClientCard> getClientCardList(int distrId, int mlkaId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd, int nkaId);

    List<ClientCard> getClientCardListByRjkam(int rjkamId, int nkaId, LocalDate dateFrom, LocalDate dateTo, int repTypeIndex);
}
