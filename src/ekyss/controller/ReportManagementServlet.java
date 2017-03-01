package ekyss.controller;

import base.servletBase;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="ReportManagementServlet",
        urlPatterns = {
                "./management/reports",
        }
)
public class ReportManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;

}
