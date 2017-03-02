package ekyss.controller;

import base.servletBase;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="ReportServlet",
        urlPatterns = {
                "/report",
        }
)
public class ReportServlet extends servletBase {

    private static final long serialVersionUID = 1L;

}
