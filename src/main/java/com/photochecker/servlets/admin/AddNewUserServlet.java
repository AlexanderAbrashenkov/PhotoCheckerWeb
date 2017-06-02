package com.photochecker.servlets.admin;

import com.photochecker.model.common.ReportType;
import com.photochecker.model.common.User;
import com.photochecker.service.common.ReportTypeService;
import com.photochecker.service.common.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by market6 on 26.04.2017.
 */
@WebServlet(name = "AddNewUserServlet",
        urlPatterns = "/reports/create_user/add_user")
public class AddNewUserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        userService = (UserService) context.getBean("userService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String login = request.getParameter("login");
        String pass = request.getParameter("password");
        String userName = request.getParameter("fio");
        String role = request.getParameter("role");
        String reportTypes = request.getParameter("report_types");
        reportTypes = reportTypes.substring(1, reportTypes.length() - 1);
        reportTypes = reportTypes.replace("\"", "");
        String[] reps = reportTypes.split(",");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        List<ReportType> allReportTypeList = ((ReportTypeService) context.getBean("reportTypeService")).getReportTypes();

        List<ReportType> newUserReports = new ArrayList<>();
        for (int i = 0; i < reps.length; i++) {
            int reportId = Integer.parseInt(reps[i]);
            newUserReports.add(allReportTypeList.stream()
                    .filter(reportType1 -> reportType1.getId() == reportId)
                    .findFirst()
                    .get());
        }

        User user = new User(0, login, userName, Integer.parseInt(role), newUserReports);

        userService.saveNewUser(user, pass);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", true)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
