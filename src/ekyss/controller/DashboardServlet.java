package ekyss.controller;

import base.servletBase;
import ekyss.model.DashboardBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name="DashboardServlet",
        urlPatterns = {
                "/dashboard",
        }
)
public class DashboardServlet extends servletBase {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DashboardBean bean = new DashboardBean();

        forwardToView(request, response, "/dashboard.jsp",bean);
    }

}
