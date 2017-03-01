package ekyss.controller;

import base.Administration;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="GroupManagementServlet",
        urlPatterns = {
                "./management/groups",
        }
)
public class GroupManagementServlet extends Administration {

    private static final long serialVersionUID = 1L;

}
