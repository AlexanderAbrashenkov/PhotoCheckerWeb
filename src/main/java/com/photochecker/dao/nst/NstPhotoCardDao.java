package com.photochecker.dao.nst;

import com.photochecker.dao.GenericDao;
import com.photochecker.model.nst.NstPhotoCard;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

/**
 * Created by market6 on 27.04.2017.
 */
public interface NstPhotoCardDao extends GenericDao<NstPhotoCard> {

    List<NstPhotoCard> findAllByDates(LocalDate startDate, LocalDate endDate);

    List<NstPhotoCard> findAllByDatesNst(int clientId, LocalDate startDate, LocalDate endDate);

    int copyPhotosToCommon(String photoTableName);

    boolean createCurrentTable(String photoTableName);
}
