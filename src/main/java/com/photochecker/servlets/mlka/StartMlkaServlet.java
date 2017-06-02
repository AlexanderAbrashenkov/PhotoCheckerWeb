package com.photochecker.servlets.mlka;

import com.photochecker.model.common.User;
import com.photochecker.model.mlka.NkaType;
import com.photochecker.service.common.CommonService;
import com.photochecker.service.mlka.NkaTypeService;
import org.springframework.context.ApplicationContext;
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
 * Created by market6 on 21.03.2017.
 */
@WebServlet(
        name = "startMlkaServlet",
        urlPatterns = "/reports/mlka"
)
public class StartMlkaServlet extends HttpServlet {

    private CommonService commonService;
    private NkaTypeService nkaTypeService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        commonService = (CommonService) context.getBean("commonService");
        nkaTypeService = (NkaTypeService) context.getBean("nkaTypeService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate startDate = commonService.getInitialStartDate();
        LocalDate endDate = commonService.getInitialEndDate();
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        List<NkaType> nkaTypeList = nkaTypeService.getNkaTypes((User) request.getSession().getAttribute("user"),
                startDate, endDate, 2);
        request.setAttribute("nkaTypeList", nkaTypeList);
        request.setAttribute("pageTitle", "Фотоотчет MLKA");
        request.setAttribute("pageCategory", "mlka");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("../WEB-INF/view/mlka/mlkaPage.jsp");
        requestDispatcher.forward(request, response);
    }
}
