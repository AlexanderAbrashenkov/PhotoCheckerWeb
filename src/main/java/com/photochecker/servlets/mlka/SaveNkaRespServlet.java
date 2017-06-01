package com.photochecker.servlets.mlka;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photochecker.model.mlka.NkaResp;
import com.photochecker.service.mlka.NkaRespService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by market6 on 01.06.2017.
 */
@WebServlet(name = "SaveNkaRespServlet",
        urlPatterns = "/reports/mlkaResp/save")

public class SaveNkaRespServlet extends HttpServlet {

    private NkaRespService nkaRespService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        nkaRespService = (NkaRespService) context.getBean("nkaRespService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<List<NkaResp>>(){}.getType();

        List<NkaResp> nkaRespList = gson.fromJson(request.getParameter("nkaRespList"), type);

        boolean succeed = nkaRespService.writeNkaResp(nkaRespList);

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("answer", succeed)
                .build();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject.toString());
    }
}
