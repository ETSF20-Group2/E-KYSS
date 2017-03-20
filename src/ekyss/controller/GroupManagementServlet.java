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
import java.util.Arrays;

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
    private final String TYPE_ASSIGNPL = "assignPl";
    private final String TYPE_DELETEPL = "deletePl";
    private final int ERR_NO_MSG = 0;
    private final int ERR_GROUP_EXISTS = 1;
    private final int ERR_GROUP_EMPTY = 2;
    private final int ERR_ASSIGN_SUCCESS = 3;
    private final int ERR_ASSIGN_EXISTS = 4;

    private String tab = "";


    private void doAdd(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void doDelete(GroupManagementBean bean) {
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
        if (bean.getAssignUser() != null || bean.getAssignGroup() != null) {
            // Användaren tryckte 'tilldela' med vald användare som ska kopplas till vald projektgrupp
            new BeanTransaction();
            if (BeanTransaction.assignUserToGroup(bean)) {
                // Lyckad tilldelning
                bean.setErrorCode(ERR_ASSIGN_SUCCESS);
                System.out.println("### doAssign_err_code: " + bean.getErrorCode());
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;
            } else {
                // Tilldelningen är lyckad - användaren tillhör redan given projektgrupp
                bean.setErrorCode(ERR_ASSIGN_EXISTS);
                System.out.println("### doAssign_err_code: " + bean.getErrorCode());
                forwardToView(request, response, "/groupmanagement.jsp", bean);
                return;
            }
        } else {
            // Användaren tryckte 'tilldela' utan att användare eller projektgrupp var vald.
            // TODO: implementera ett felmeddelande
        }
    }

    private void doDeletePl(GroupManagementBean bean) {
        System.out.println(Arrays.toString(bean.getRemovePl()));
        if (bean.getRemovePl() != null) {
            // Användaren tryckte 'ta bort' med en eller flera grupper som ska tas bort.
            new BeanTransaction();
            BeanTransaction.deletePlGroup(bean.getRemovePl());
        } else {
            // Användaren tryckte 'ta bort' utan att markera nån grupp.
            // TODO: implementera ett felmeddelande
        }
    }

    private void doAssignPl(GroupManagementBean bean, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (bean.getAssignUserPl() != null || bean.getAssignGroupPl() != null) {
            // Användaren tryckte 'tilldela' med vald användare som ska kopplas till vald projektgrupp
            new BeanTransaction();
            boolean assigned = BeanTransaction.assignPlToGroup(bean);
            System.out.println("Assigned: " + assigned);
            if (assigned) {
                // Lyckad tilldelning
                bean.setErrorCode(ERR_ASSIGN_SUCCESS);
                System.out.println("### doAssign_err_code: " + bean.getErrorCode());
            //    forwardToView(request, response, "/groupmanagement.jsp", bean);
                System.out.println("--------------------");
                return;

            } else {
                // Tilldelningen är lyckad - användaren tillhör redan given projektgrupp
                bean.setErrorCode(ERR_ASSIGN_EXISTS);
                System.out.println("### doAssign_err_code: " + bean.getErrorCode());
               // forwardToView(request, response, "/groupmanagement.jsp", bean);
                System.out.println("--------------------");
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
            System.out.println("--------------------");
            System.out.println(bean.getType());
            System.out.println(bean.getType().equals(TYPE_ASSIGNPL));
            if (bean.getType().equals(TYPE_ADD)) {

                System.out.println("IN ADD");
                // Förfrågning kommer från add-formuläret
                bean.setTab("add");
                doAdd(bean, request, response);
            } else if (bean.getType().equals(TYPE_DELETE)) {

                System.out.println("IN DELETE");
                // Förfrågning kommer från delete-formuläret
                bean.setTab("delete");
                doDelete(bean);
            } else if (bean.getType().equals(TYPE_ASSIGN)) {
                System.out.println("IN ASSIGN");
                // Förfrågning kommer från assign-formuläret
                bean.setTab("assign");
                doAssign(bean, request, response);
            } else if (bean.getType().equals(TYPE_ASSIGNPL)) {

                System.out.println("IN ASSIGNPL");
                System.out.println(bean.getAssignGroupPl() + ", " + bean.getAssignUserPl());
                tab = "assignPl";
                doAssignPl(bean, request, response);
            } else if (bean.getType().equals(TYPE_DELETEPL)) {

                System.out.println("IN DELETEPL");
                tab = "assignPl";
                doDeletePl(bean);
            }


            else {
                System.out.println("Förmulärtyp okänd! Inputattributet 'type' saknas vid POST-anrop: getParameter: [" + request.getParameter("type") + "]; getText: [" + bean.getType() + "]");
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
            GroupManagementBean bean = BeanFactory.getGroupManagementBean();
            if(tab.equals("assignPl")) bean.setTab(tab);
            forwardToView(request, response, "/groupmanagement.jsp", bean);
            System.out.println("--------------------");
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect("/");
        }
    }

}
