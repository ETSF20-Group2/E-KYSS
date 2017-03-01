package ekyss.controller;

import base.Administration;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="UserManagementServlet",
        urlPatterns = {
                "./management/users",
        }
)
public class UserManagementServlet extends Administration {

    private static final long serialVersionUID = 1L;

}
