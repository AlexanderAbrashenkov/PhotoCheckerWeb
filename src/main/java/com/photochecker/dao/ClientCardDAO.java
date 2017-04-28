package com.photochecker.dao;

import com.photochecker.model.ClientCard;
import com.photochecker.model.Lka;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 27.04.2017.
 */
public interface ClientCardDAO {
    public boolean create(ClientCard clientCard);
    public ClientCard find(int id);
    public List<ClientCard> findAll();
    public boolean update(ClientCard clientCard);
    public List<ClientCard> findAllByDatesAndLka(Lka lka, LocalDate startDate, LocalDate endDate);
}
