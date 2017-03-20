package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
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
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            doGet(request, response);
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession(true);
            DashboardBean bean = null;
            if (session.getAttribute("name").equals("admin")) {
                bean = new DashboardBean();
            } else if ((boolean) session.getAttribute("ProjectLeader")) {
                if (request.getParameter("show") != null) {
                    bean = BeanFactory.getDashboardBeanPL(request.getParameter("show"), (String) session.getAttribute("group"), null);
                } else {
                    bean = BeanFactory.getDashboardBeanPL("all", (String) session.getAttribute("group"), null);
                }
            } else {
                bean = BeanFactory.getDashboardBean((String) session.getAttribute("name"), (String) session.getAttribute("group"));
            }
            forwardToView(request, response, "/dashboard.jsp",bean);
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
    }

}
