package com.photochecker.servlets.admin;

import com.photochecker.model.Upload;
import com.photochecker.service.common.UploadService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;

/**
 * Created by market6 on 07.04.2017.
 */
@WebServlet(name = "UploadServlet",
urlPatterns = "/reports/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private UploadService uploadService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        uploadService = (UploadService) context.getBean("uploadService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("pageTitle", "Загрузка данных");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/uploadPage.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent, "UTF-8"));

        String date = fileName.substring(fileName.indexOf("_") + 1, fileName.length() - 4);

        String result = uploadService.uploadDatas(reader, date);

        reader.close();

        request.setAttribute("resultOfUpload", "Файл с данными за " + date + " получен. Обработано: " + result);

        request.setAttribute("pageTitle", "Загрузка данных");
        request.setAttribute("pageCategory", "administrate");

        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/uploadPage.jsp");
        dispatcher.forward(request, response);
    }
}
