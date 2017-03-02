package ekyss.controller;

import base.servletBase;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="UserServlet",
        urlPatterns = {
                "/user",
        }
)
public class UserServlet extends servletBase {

    private static final long serialVersionUID = 1L;

}
