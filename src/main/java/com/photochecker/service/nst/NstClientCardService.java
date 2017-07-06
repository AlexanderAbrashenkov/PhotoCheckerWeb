package com.photochecker.service.nst;

import com.photochecker.model.nst.NstClientCard;

import java.time.LocalDate;
import java.util.List;

public interface NstClientCardService {
    List<NstClientCard> getClientCardList(int formatId, int nstOblId, LocalDate startDate, LocalDate endDate, int repTypeIndex);
}
