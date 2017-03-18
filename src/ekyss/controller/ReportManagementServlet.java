package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
import ekyss.model.ReportManagementBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name="ReportManagementServlet",
        urlPatterns = {
                "/management/reports",
        }
)
public class ReportManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_SIGN = "sign";
    private final String TYPE_UNSIGN = "unsign";
    private final String TYPE_REMOVE = "remove";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // TODO: Kolla om användaren är inloggad, samt att användaren har behörighet att visa sidan (PL).
        if(true){
            // Användaren är inloggad och har behörighet
            ReportManagementBean rmb = new ReportManagementBean();
            BeanUtilities.populateBean(rmb, request);
            if(rmb.getType().equals(TYPE_SIGN)){
                BeanTransaction.signReports(rmb);
            }
            else if(rmb.getType().equals(TYPE_UNSIGN)) {
                BeanTransaction.unsignReport(rmb);
            }
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Kolla om användaren är inloggad, samt att användaren har behörighet att visa sidan.
        if(true) {
            ReportManagementBean bean = new ReportManagementBean();
            forwardToView(request, response, "/reportmanagement.jsp", bean);
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }

    }

}
