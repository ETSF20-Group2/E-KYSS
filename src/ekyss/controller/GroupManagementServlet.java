package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
import ekyss.model.BeanTransaction;
import ekyss.model.DashboardBean;
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
public class GroupManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        GroupManagementBean bean = null;
        forwardToView(request, response, "/groupmanagement.jsp",bean);

    }

}
