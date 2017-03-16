package ekyss.controller;

import base.servletBase;
import ekyss.model.*;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name="GroupManagementServlet",
        urlPatterns = {
                "/management/groups",
        }
)
public class GroupManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_ADD = "add";
    private final String TYPE_DELETE = "delete";
    private final int ERR_NO_MSG = 0;
    private final int ERR_GROUP_EXISTS = 1;
    private final int ERR_GROUP_EMPTY = 2;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Kolla om användaren är inloggad, samt att användaren har behörighet att visa sidan.
        if (true) {
            // Användaren är inloggad och har behörighet
            GroupManagementBean bean = BeanFactory.getGroupManagementBean();
            BeanUtilities.populateBean(bean, request);
            if (bean.getType() != null) {
                if (bean.getType().equals(TYPE_ADD)) {
                    // Förfrågning kommer från add-formuläret
                    if (!bean.getAllGroups().contains(bean.getGroupName())) {
                        // Gruppnamnet finns inte i databasen.
                        if (!bean.getGroupName().isEmpty() || !bean.getGroupName().equals("")) {
                            // Vi har här ett unikt gruppnamn som ska sparas i databasen
                            new BeanTransaction().createNewProjectGroup(bean.getGroupName());
                        } else {
                            // Gruppnamnet är tomt
                            bean.setErrorCode(ERR_GROUP_EMPTY);
                            forwardToView(request, response, "/groupmanagement.jsp", bean);
                            return;
                        }
                    } else {
                        // Gruppnamnet finns redan i databasen.
                        bean.setErrorCode(ERR_GROUP_EXISTS);
                        forwardToView(request, response, "/groupmanagement.jsp", bean);
                        return;
                    }

                } else if (bean.getType().equals(TYPE_DELETE)) {
                    // Förfrågning kommer från delete-formuläret
                    // TODO: Implementera borttagning av grupper.
                }
            }
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Kolla om användaren är inloggad, samt att användaren har behörighet att visa sidan.
        if (true) {
            // Användaren är inloggad och har behörighet
            GroupManagementBean bean = BeanFactory.getGroupManagementBean();
            forwardToView(request, response, "/groupmanagement.jsp", bean);
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
    }

}
