package com.photochecker.servlets.lkaDmp.ajax_servlets;

import com.photochecker.model.common.Channel;
import com.photochecker.service.common.ChannelService;
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

@WebServlet(
        name = "downloadLkaDmpChannels",
        urlPatterns = "/reports/lkaDmp/getChannels"
)
public class DownloadChannelsServlet extends HttpServlet {
    private ChannelService channelService;

    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        channelService = (ChannelService) context.getBean("channelService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sDateFrom = request.getParameter("dateFrom");
        String sDateTo = request.getParameter("dateTo");
        int distrId = Integer.parseInt(request.getParameter("distrId"));

        LocalDate dateFrom = LocalDate.parse(sDateFrom);
        LocalDate dateTo = LocalDate.parse(sDateTo);

        List<Channel> channelList = channelService.getChannels(distrId, dateFrom, dateTo, 1);
        request.setAttribute("channelList", channelList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/lkaDmp/ajax_parts/channelOptions.jsp");
        dispatcher.forward(request, response);
    }
}
