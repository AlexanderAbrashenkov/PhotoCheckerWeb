package com.photochecker.servlets;

import com.photochecker.models.Upload;
import com.photochecker.models.lka.LkaExpert;

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

        int counter = Upload.uploadDataProcess(reader, date);

        request.setAttribute("resultOfUpload", "Файл с данными за " + date + " получен. Всего " + counter + " записей в файле обработано.");

        request.setAttribute("pageTitle", "Загрузка данных");
        request.setAttribute("pageCategory", "administrate");

        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/uploadPage.jsp");
        dispatcher.forward(request, response);
    }
}
