package ekyss.controller;

import base.LogIn;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name="LoginServlet",
        urlPatterns = {
                "./",
                "./login",
                "./logout",
        }
)
public class LoginServlet extends LogIn {

    private static final long serialVersionUID = 1L;

}
