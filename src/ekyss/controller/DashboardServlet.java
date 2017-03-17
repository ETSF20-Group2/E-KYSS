package ekyss.controller;

import base.servletBase;
import ekyss.model.DashboardBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.IOException;
import java.util.Enumeration;

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

        DashboardBean bean = null;

        HttpSession session = request.getSession(true);

        forwardToView(request, response, "/dashboard.jsp",bean);
    }

}
