package com.photochecker.controllers.nst;

import com.photochecker.model.common.User;
import com.photochecker.model.nst.NstStat;
import com.photochecker.service.nst.NstStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class NstStatController {

    @Autowired
    private NstStatService nstStatService;

    @PostMapping(value = "/reports/nst/getStat", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public NstStat getNstStat (@RequestParam("dateFrom") String dateFromS,
                               @RequestParam("dateTo") String dateToS,
                               @RequestParam("formatId") int formatId,
                               @RequestParam("oblId") int oblId,
                               HttpSession session) {

        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);

        return nstStatService.getStat((User) session.getAttribute("user"), formatId, oblId, dateFrom, dateTo);
    }
}
