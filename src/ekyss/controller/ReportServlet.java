package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
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
/**
 *  Denna klass är servlet för webbsidan "Veckorapportering".
 */
public class ReportServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_CREATE = "create";
    private final String TYPE_UPDATE = "update";
    private final String TYPE_REMOVE = "remove";
    private final String TYPE_SELECT = "weekSelect";

    private final int ERR_CREATED = 1;
    private final int ERR_UPDATED = 2;
    private final int ERR_REMOVED = 3;
    private final int ERR_DUPLICATE = 4;
    private final int ERR_NO_GROUP = 5;
    private final int ERR_UNKNOWN = 6;

    private String tab = "create";

    private int err_code = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String user = (String) session.getAttribute("name");
            String group = (String) session.getAttribute("group");
            ReportBean rb = new ReportBean();
            BeanUtilities.populateBean(rb, request);

            rb.setUser(user);
            rb.setGroup(group);
            if (rb.getType().equals(TYPE_CREATE)) {
                Integer code = BeanTransaction.createTimeReport(rb);
                switch (code) {
                    case 0: err_code = ERR_CREATED; break;
                    case 1: err_code = ERR_DUPLICATE; break;
                    case 2: err_code = ERR_NO_GROUP; break;
                    default: err_code = ERR_UNKNOWN; break;
                }
                tab = "create";
            } else if (rb.getType().equals(TYPE_UPDATE)) {
                BeanTransaction.updateTimeReport(rb);
                tab = "update";
                err_code = ERR_UPDATED;
            } else if (rb.getType().equals(TYPE_REMOVE)) {
                BeanTransaction.removeTimeReport(rb);
                tab = "remove";
                err_code = ERR_REMOVED;
            }
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect(request.getContextPath() + "/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String user = (String) session.getAttribute("name");
            String group = (String) session.getAttribute("group");
            ReportBean bean = BeanFactory.getReportBean(user, group);
            BeanUtilities.populateBean(bean, request);
            bean.setErr_code(err_code);
            if(bean.getType().equals(TYPE_SELECT)){
                bean = BeanFactory.fillReportBean(bean, user, group, bean.getWeek());
                tab = "update";
            }
            bean.setTab(tab);
            forwardToView(request, response, "/report.jsp", bean);
            err_code = 0;
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
