package com.photochecker.service.nst;

import com.photochecker.model.nst.NstPhotoCard;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public interface NstPhotoCardService {

    List<NstPhotoCard> getPhotoListNst(int clientId, LocalDate dateFrom, LocalDate dateTo);

    void archivatePhotoIfNeed();
}
