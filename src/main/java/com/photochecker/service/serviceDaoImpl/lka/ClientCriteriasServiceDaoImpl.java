package com.photochecker.service.serviceDaoImpl.lka;

import com.photochecker.dao.lka.ClientCriteriasDao;
import com.photochecker.model.lka.ClientCriterias;
import com.photochecker.service.lka.ClientCriteriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 17.05.2017.
 */
public class ClientCriteriasServiceDaoImpl implements ClientCriteriasService {
    @Autowired
    private ClientCriteriasDao clientCriteriasDao;

    /*public ClientCriteriasServiceDaoImpl() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        clientCriteriasDao = (ClientCriteriasDao) context.getBean("clientCriteriasDao");
    }*/

    @Override
    public boolean saveCriterias(ClientCriterias clientCriterias) {
        boolean succeed;
        int id = -1;
        List<ClientCriterias> savedClientCriterias = clientCriteriasDao.findAllByClientAndDates(clientCriterias.getClientId(),
                clientCriterias.getDateFrom(), clientCriterias.getDateTo());

        if (null != savedClientCriterias && savedClientCriterias.size() > 0) {
            succeed = clientCriteriasDao.update(clientCriterias);
        } else {
            id = clientCriteriasDao.save(clientCriterias);
            succeed = true;
        }

        return succeed;
    }

    @Override
    public ClientCriterias getSavedCriterias(int clientId, LocalDate dateFrom, LocalDate dateTo) {
        ClientCriterias clientCriterias = clientCriteriasDao.findAllByClientAndDates(clientId, dateFrom, dateTo).get(0);
        return clientCriterias;
    }
}
