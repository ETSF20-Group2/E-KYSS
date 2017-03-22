package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
import ekyss.model.GroupManagementBean;

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
/**
 *  Denna klass är servlet för webbsidan "Projektgrupphantering".
 */
public class GroupManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_ADD = "add";
    private final String TYPE_DELETE = "delete";
    private final String TYPE_ASSIGN = "assign";
    private final String TYPE_ASSIGNPL = "assignPl";
    private final String TYPE_DELETEPL = "deletePl";
    private final int ERR_NO_MSG = 0;
    private final int ERR_GROUP_EXISTS = 1;
    private final int ERR_GROUP_EMPTY = 2;
    private final int ERR_ASSIGN_SUCCESS = 3;
    private final int ERR_ASSIGN_EXISTS = 4;
    private final int ERR_DELETE_SUCCESS = 5;
    private final int ERR_DELETE_FAIL = 6;
    private final int ERR_ASSIGNPL_SUCCESS = 7;
    private final int ERR_ASSIGNPL_FAIL = 8;
    private final int ERR_DELETEPL_SUCCESS = 9;
    private final int ERR_DELETEPL_FAIL = 10;
    private final int ERR_ADD_SUCCESS = 11;


    private void doAdd(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!bean.getAllGroups().contains(bean.getGroupName())) {
            // Gruppnamnet finns inte i databasen.
            if (!bean.getGroupName().isEmpty() || !bean.getGroupName().equals("")) {
                // Vi har här ett unikt gruppnamn som ska sparas i databasen
                new BeanTransaction();
                BeanTransaction.createNewProjectGroup(bean.getGroupName());
                bean.setErrorCode(ERR_ADD_SUCCESS);
                bean = BeanFactory.fillGroupManagementBean(bean);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;

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
        if (bean.getDeleteGroup() != null) {
            // Användaren tryckte 'ta bort' med en eller flera grupper som ska tas bort.
            bean.setErrorCode(ERR_DELETE_SUCCESS);
            new BeanTransaction();
            BeanTransaction.deleteProjectGroup(bean.getDeleteGroup());
            bean = BeanFactory.fillGroupManagementBean(bean);
            forwardToView(request, response, "/groupmanagement.jsp", bean);
            return;
        } else {
            bean.setErrorCode(ERR_DELETE_FAIL);
            forwardToView(request, response, "/groupmanagement.jsp", bean);
            return;
        }
    }

    private void doAssign(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (bean.getAssignUser() != null || bean.getAssignGroup() != null) {
            // Användaren tryckte 'tilldela' med vald användare som ska kopplas till vald projektgrupp
            new BeanTransaction();
            if (BeanTransaction.assignUserToGroup(bean)) {
                // Lyckad tilldelning
                bean.setErrorCode(ERR_ASSIGN_SUCCESS);
                bean = BeanFactory.fillGroupManagementBean(bean);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;
            } else {
                // Tilldelningen är lyckad - användaren tillhör redan given projektgrupp
                bean.setErrorCode(ERR_ASSIGN_EXISTS);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;
            }
        } else {
            // Användaren tryckte 'tilldela' utan att användare eller projektgrupp var vald.
            // TODO: implementera ett felmeddelande
        }
    }

    private void doDeletePl(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (bean.getRemovePl() != null) {
            // Användaren tryckte 'ta bort' med en eller flera grupper som ska tas bort.
            bean.setErrorCode(ERR_DELETEPL_SUCCESS);
            new BeanTransaction();
            BeanTransaction.deletePlGroup(bean.getRemovePl());
            bean = BeanFactory.fillGroupManagementBean(bean);
            forwardToView(request, response, "/groupmanagement.jsp", bean);
            return;

        } else {
            // Användaren tryckte 'ta bort' utan att markera nån grupp.
            bean.setErrorCode(ERR_DELETEPL_FAIL);
            forwardToView(request, response, "/groupmanagement.jsp", bean);
            return;
        }
    }

    private void doAssignPl(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (bean.getAssignUserPl() != null || bean.getAssignGroupPl() != null) {
            // Användaren tryckte 'tilldela' med vald användare som ska kopplas till vald projektgrupp
            new BeanTransaction();
            if (BeanTransaction.assignPlToGroup(bean)) {
                // Lyckad tilldelning
                bean.setErrorCode(ERR_ASSIGNPL_SUCCESS);
                bean = BeanFactory.fillGroupManagementBean(bean);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;

            } else {
                // Tilldelningen är lyckad - användaren tillhör redan given projektgrupp
                bean.setErrorCode(ERR_ASSIGNPL_FAIL);
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;

            }
        } else {
            // Användaren tryckte 'tilldela' utan att användare eller projektgrupp var vald.
            // TODO: implementera ett felmeddelande
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            GroupManagementBean bean = BeanFactory.getGroupManagementBean();
            BeanUtilities.populateBean(bean, request);
            if (bean.getType().equals(TYPE_ADD)) {
                // Förfrågning kommer från add-formuläret
                bean.setTab("add");
                doAdd(bean, request, response);
                return;
            } else if (bean.getType().equals(TYPE_DELETE)) {
                // Förfrågning kommer från delete-formuläret
                bean.setTab("delete");
                doDelete(bean,request,response);
                return;
            } else if (bean.getType().equals(TYPE_ASSIGN)) {
                // Förfrågning kommer från assign-formuläret
                bean.setTab("assign");
                doAssign(bean, request, response);
                return;
            } else if (bean.getType().equals(TYPE_ASSIGNPL)) {
                bean.setTab("assignPl");
                doAssignPl(bean, request, response);
                return;
            } else if (bean.getType().equals(TYPE_DELETEPL)) {
                bean.setTab("assignPl");
                doDeletePl(bean, request, response);
                return;
            }


            else {
                System.out.println("Förmulärtyp okänd! Inputattributet 'type' saknas vid POST-anrop: getParameter: [" + request.getParameter("type") + "]; getText: [" + bean.getType() + "]");
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
            GroupManagementBean bean = BeanFactory.getGroupManagementBean();
            forwardToView(request, response, "/groupmanagement.jsp", bean);
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}
