package ekyss.controller;

import base.servletBase;
import com.sun.deploy.net.HttpResponse;
import ekyss.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    private final String TYPE_ASSIGN = "assign";
    private final int ERR_NO_MSG = 0;
    private final int ERR_GROUP_EXISTS = 1;
    private final int ERR_GROUP_EMPTY = 2;
    private final int ERR_ASSIGN_SUCCESS = 3;
    private final int ERR_ASSIGN_EXISTS = 4;

    private void doAdd(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Förfrågning kommer från add-formuläret
        if (!bean.getAllGroups().contains(bean.getGroupName())) {
            // Gruppnamnet finns inte i databasen.
            if (!bean.getGroupName().isEmpty() || !bean.getGroupName().equals("")) {
                // Vi har här ett unikt gruppnamn som ska sparas i databasen
                new BeanTransaction();
                BeanTransaction.createNewProjectGroup(bean.getGroupName());
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
    }

    private void doDelete(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Förfrågning kommer från delete-formuläret
        if (bean.getDeleteGroup() != null) {
            // Användaren tryckte 'ta bort' med en eller flera grupper som ska tas bort.
            new BeanTransaction();
            BeanTransaction.deleteProjectGroup(bean.getDeleteGroup());
        } else {
            // Användaren tryckte 'ta bort' utan att markera nån grupp.
            // TODO: implementera ett felmeddelande
        }
    }

    private void doAssign(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Förfrågning kommer från assign-formuläret
        if (bean.getAssignUser() != null || bean.getAssignGroup() != null) {
            // Användaren tryckte 'tilldela' med vald användare som ska kopplas till vald projektgrupp
            if (BeanTransaction.assignUserToGroup(bean)) {
                // Lyckad tilldelning
                bean.setErrorCode(ERR_ASSIGN_SUCCESS);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;
            } else {
                // Tilldelningen är lyckad - användaren tillhör redan given projektgrupp
                bean.setErrorCode(ERR_ASSIGN_EXISTS);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO: Kolla om användaren är inloggad, samt att användaren har behörighet att visa sidan.
        if (true) {
            // Användaren är inloggad och har behörighet
            GroupManagementBean bean = BeanFactory.getGroupManagementBean();
            BeanUtilities.populateBean(bean, request);
           if (bean.getType().equals(TYPE_ADD)) {
                   doAdd(bean,request,response);
                   return;
            } else if (bean.getType().equals(TYPE_DELETE)) {
                    doDelete(bean, request, response);
                    return;
            } else if (bean.getType().equals(TYPE_ASSIGN)) {
                    doAssign(bean, request, response);
                    return;
            } else {
                System.out.println("Förmulärtyp okänd! Inputattributet 'type' saknas vid POST-anrop.");
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
