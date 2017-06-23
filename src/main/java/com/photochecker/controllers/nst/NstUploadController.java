package com.photochecker.controllers.nst;

import com.photochecker.service.nst.NstUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class NstUploadController {

    @Autowired
    private NstUploadService nstUploadService;

    @GetMapping("/reports/nstUpload")
    public ModelAndView showNstUploadPage(@Value("${resVer}") String resVer) {
        ModelAndView modelAndView = new ModelAndView("nst/nstUpload");
        modelAndView.addObject("pageTitle", "Загрузка данных");
        modelAndView.addObject("pageCategory", "administrate");
        modelAndView.addObject("resVer", resVer);

        return modelAndView;
    }

    @PostMapping("/reports/nstUpload")
    public ModelAndView uploadNstFile (@RequestParam("file") MultipartFile file,
                                       @Value("${resVer}") String resVer) {

        ModelAndView modelAndView = new ModelAndView("nst/nstUpload");
        modelAndView.addObject("pageTitle", "Загрузка данных");
        modelAndView.addObject("pageCategory", "administrate");
        modelAndView.addObject("resVer", resVer);


        if (file.isEmpty()) {
            modelAndView.addObject("resultOfUpload", "Файл не выбран");
            return modelAndView;
        }

        try {
            long start = System.currentTimeMillis();
            InputStream fileContent = file.getInputStream();
            String fileName = file.getOriginalFilename();
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));

            String dateFrom = fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf("-"));
            String dateTo = fileName.substring(fileName.indexOf("-") + 1, fileName.lastIndexOf("."));

            String result = nstUploadService.uploadDatas(reader, dateFrom, dateTo);

            reader.close();

            long end = System.currentTimeMillis();

            modelAndView.addObject("resultOfUpload", "Файл с данными за " + dateFrom + "-" + dateTo + " получен. <br><br> Обработано: " + result +
                    "<br><br>Время загрузки: " + (end - start) / 1000 + " сек.");

            return modelAndView;
        } catch (IOException e) {
            e.printStackTrace();
            modelAndView.addObject("resultOfUpload", "Не удалось загрузить файл");
            return modelAndView;
        }
    }
}
