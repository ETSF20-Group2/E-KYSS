package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
import ekyss.model.ReportBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


        ReportBean rb = new ReportBean();
        BeanUtilities.populateBean(rb, request);
        // TODO Kolla om användare är inloggad och har behörighet att visa sidan.
        if(true) {
            if (rb.getType().equals(TYPE_CREATE)) {
                BeanTransaction.createTimeReport(rb);
            } else if (rb.getType().equals(TYPE_UPDATE)) {
                BeanTransaction.updateTimeReport(rb);
            } else if (rb.getType().equals(TYPE_REMOVE)) {
                BeanTransaction.removeTimeReport(rb);
            }
        }
        else {
            response.sendRedirect("/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Kolla om användaren är inloggad, samt att användaren har behörighet att visa sidan.
        if(true) {
            ReportBean bean = new ReportBean();
            forwardToView(request, response, "/report.jsp", bean);
        }
        else{
            response.sendRedirect("/");
        }

    }

}
