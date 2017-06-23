package com.photochecker.service.common;

import com.photochecker.model.common.PhotoCard;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface PhotoCardService {
    public List<PhotoCard> getPhotoList(int clientId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);

    List<PhotoCard> getPhotoListNst(int clientId, LocalDate dateFrom, LocalDate dateTo, int repTypeInd);
}
