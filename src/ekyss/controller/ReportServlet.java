package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
import ekyss.model.ReportBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name="ReportServlet",
        urlPatterns = {
                "/report",
        }
)
public class ReportServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_CREATE = "create";
    private final String TYPE_UPDATE = "update";
    private final String TYPE_REMOVE = "remove";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (true) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String user = (String) session.getAttribute("name");
            String group = (String) session.getAttribute("group");
            ReportBean rb = new ReportBean();
            System.out.println(1);
            BeanUtilities.populateBean(rb, request);
            rb.setUser(user);
            rb.setGroup(group);
            System.out.print(rb.getType() + " ");
            System.out.println(rb.getD_11());
            System.out.println(rb.getGroup() + " " + rb.getUser() + " " + rb.getWeek());
            if (rb.getType().equals(TYPE_CREATE)) {
                System.out.println(2);
                BeanTransaction.createTimeReport(rb);
            } else if (rb.getType().equals(TYPE_UPDATE)) {
                BeanTransaction.updateTimeReport(rb);
            } else if (rb.getType().equals(TYPE_REMOVE)) {
                BeanTransaction.removeTimeReport(rb);
            }
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(3);
        if (true) {
            // Användaren är inloggad och har behörighet
            ReportBean bean = new ReportBean();
            forwardToView(request, response, "/report.jsp", bean);
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }

    }

}
