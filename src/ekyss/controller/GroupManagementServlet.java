package ekyss.controller;

import base.servletBase;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="GroupManagementServlet",
        urlPatterns = {
                "/management/groups",
        }
)
public class GroupManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;

}
