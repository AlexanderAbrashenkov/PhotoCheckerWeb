package com.photochecker.schedulers;

import com.photochecker.service.nst.NstStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class NstStatCleanerScheduler {

    @Autowired
    private NstStatService nstStatService;

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanNstStatField() {
        int rowsAffected = 0;
        while (rowsAffected == 0) {
            rowsAffected = nstStatService.cleanCheckedToday();
        }
        rowsAffected = 0;
    }
}
