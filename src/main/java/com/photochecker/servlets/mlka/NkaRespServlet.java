package com.photochecker.servlets.mlka;

import com.photochecker.model.common.User;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.common.UserService;
import com.photochecker.service.mlka.NkaRespService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by market6 on 20.04.2017.
 */
@WebServlet(name = "MlkaResponsibServlet",
urlPatterns = "/reports/mlkaResp")
public class NkaRespServlet extends HttpServlet {

    private NkaRespService nkaRespService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        nkaRespService = (NkaRespService) context.getBean("nkaRespService");
        userService = (UserService) context.getBean("userService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<NkaResp> nkaRespList = nkaRespService.getAllNkaResp();
        List<User> respUsers = userService.getRespUsers().get(new Integer(2));
        request.setAttribute("respList", nkaRespList);
        request.setAttribute("respUsers", respUsers);
        request.setAttribute("pageTitle", "Ответственные");
        request.setAttribute("pageCategory", "administrate");
        RequestDispatcher dispatcher = request.getRequestDispatcher("../WEB-INF/view/mlka/nkaRespPage.jsp");
        dispatcher.forward(request, response);
    }
}
