package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
import ekyss.model.ReportManagementBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    private String tab = "sign";
    private int err_code = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String group = (String) session.getAttribute("group");
            ReportManagementBean rmb = new ReportManagementBean();
            BeanUtilities.populateBean(rmb, request);
            rmb.setGroup(group);
            if(rmb.getType().equals(TYPE_SIGN)){
                BeanTransaction.signReports(rmb);
                tab = "sign";
                err_code = 1;
            }
            else if(rmb.getType().equals(TYPE_UNSIGN)) {
                BeanTransaction.unsignReport(rmb);
                tab = "unsign";
                err_code = 2;
            }
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String group = (String) session.getAttribute("group");
            ReportManagementBean bean = BeanFactory.getReportManagementBean(group);
            bean.setTab(tab);
            bean.setErr_code(err_code);
            forwardToView(request, response, "/reportmanagement.jsp", bean);
            err_code = 0;
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }

    }

}
