package com.photochecker.servlets.lkaDmp.ajax_servlets;

import com.photochecker.model.PhotoCard;
import com.photochecker.service.common.PhotoCardService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by market6 on 28.03.2017.
 */
@WebServlet(
        name = "downloadLkaDmpPhotos",
        urlPatterns = "/reports/lkaDmp/getPhotos"
)
public class DownloadPhotosServlet extends HttpServlet {

    private PhotoCardService photoCardService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        photoCardService = (PhotoCardService) context.getBean("photoCardService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int clientId = Integer.parseInt(request.getParameter("clientId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        List<PhotoCard>  photoCardList = photoCardService.getPhotoList(clientId, dateFrom, dateTo, 1);

        request.setAttribute("photoList", photoCardList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lkaDmp/ajax_parts/photoPane.jsp");
        dispatcher.forward(request, response);
    }
}
