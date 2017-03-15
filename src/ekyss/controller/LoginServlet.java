package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
import ekyss.model.BeanUtilities;
import ekyss.model.Database;
import ekyss.model.LoginBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name="LoginServlet",
        urlPatterns = {
                "",
                "/login",
                "/logout",
        }
)
public class LoginServlet extends servletBase {

    private static final long serialVersionUID = 1L;

    private final int LOGIN_NO_MSG = 0;
    private final int LOGIN_WRONG_CREDENTIAL = 1;
    private final int LOGIN_SING_OUT_DO_TO_INACTIVITY = 2;
    private final int LOGIN_SING_OUT = 3;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        LoginBean bean = new LoginBean();
        BeanUtilities.populateBean(bean, request);

        // Kontrollerar om viklen typ av inloggning som används
        if (bean.getSelectedGroup() == null) {
            bean.setAdminLogin(true);
        } else {
            bean.setAdminLogin(false);
        }

        // Kontrollerar om inloggningsuppgifterna är korrekta
        BeanFactory.checkLoginBean(bean);

        if (bean.isLogin()) {
            // Loggar in användaren
            session.setAttribute("name", bean.getUsername());
            session.setAttribute("group", bean.getSelectedGroup());
            session.setAttribute("state", LOGIN_TRUE);
            response.sendRedirect("/dashboard");
            return;
        } else {
            // Inloggninsuppgifter inkorrekta, skickas tillbaka till inloggning.
            request.setAttribute("msg_code", LOGIN_WRONG_CREDENTIAL);
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        LoginBean bean = BeanFactory.getLoginBean();

        if (loggedIn(request) && request.getServletPath().equals("/logout")) {
            session.setAttribute("state", LOGIN_FALSE);
            bean.setErrorCode(LOGIN_SING_OUT);
        } else if (request.getServletPath().equals("/login")) {
            bean.setAdminLogin(true);
        } else {
            bean.setAdminLogin(false);
        }

        forwardToView(request, response, "/login.jsp", bean);
    }

}
