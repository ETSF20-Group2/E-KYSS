package ekyss.controller;

import base.servletBase;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="UserManagementServlet",
        urlPatterns = {
                "/management/users",
        }
)
public class UserManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;

}
