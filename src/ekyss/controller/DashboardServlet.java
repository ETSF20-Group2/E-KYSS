package ekyss.controller;

import base.servletBase;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="DashboardServlet",
        urlPatterns = {
                "/dashboard",
        }
)
public class DashboardServlet extends servletBase {

    private static final long serialVersionUID = 1L;

}
