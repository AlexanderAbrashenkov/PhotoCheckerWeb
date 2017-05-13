package com.photochecker.dao.common;

import com.photochecker.dao.GenericDAO;
import com.photochecker.model.ClientCard;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCardDAO extends GenericDAO<ClientCard> {

    List<ClientCard> findAllByLkaAndDates(Lka lka, LocalDate startDate, LocalDate endDate);
}
