package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanFactory;
import ekyss.model.BeanUtilities;
import ekyss.model.LoginBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name="LoginServlet",
        urlPatterns = {
                "",
                "/login",
                "/logout",
        }
)
/**
 *  Denna klass är servlet för inloggningen till webbapplikationen.
 */
public class LoginServlet extends servletBase {

    private static final long serialVersionUID = 1L;

    private final int LOGIN_NO_MSG = 0;
    private final int LOGIN_WRONG_CREDENTIAL = 1;
    private final int LOGIN_SING_OUT_DO_TO_INACTIVITY = 2;
    private final int LOGIN_SING_OUT = 3;
    private final int LOGIN_OLD_LOGIN = 4;

    private final int LOGIN_TYPE_ADMIN = 0;
    private final int LOGIN_TYPE_COMMON = 1;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        LoginBean bean = new LoginBean();
        BeanUtilities.populateBean(bean, request);

        // Kontrollerar viklen typ av inloggning som används
        if (bean.getSelectedGroup() == null) {
            bean.setAdminLogin(true);
        } else {
            bean.setAdminLogin(false);
        }

        if (loggedIn(request)) {
            session.setAttribute("state", LOGIN_FALSE);
            request.setAttribute("msg_code", LOGIN_OLD_LOGIN);
            if (bean.getAdminLogin()) {
                request.setAttribute("login_type", LOGIN_TYPE_ADMIN);
            } else {
                request.setAttribute("login_type", LOGIN_TYPE_COMMON);
            }
        } else {
            // Kontrollerar om inloggningsuppgifterna är korrekta
            BeanFactory.checkLoginBean(bean);
            if (bean.isLogin()) {
                // Loggar in användaren
                session.setAttribute("name", bean.getUsername());
                session.setAttribute("group", bean.getSelectedGroup());
                session.setAttribute("state", LOGIN_TRUE);
                session.setAttribute("ProjectLeader", BeanFactory.isProjectLeader(bean));
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            } else {
                // Inloggninsuppgifter inkorrekta, skickas tillbaka till inloggning.
                request.setAttribute("msg_code", LOGIN_WRONG_CREDENTIAL);
                if (bean.getAdminLogin()) {
                    request.setAttribute("login_type", LOGIN_TYPE_ADMIN);
                } else {
                    request.setAttribute("login_type", LOGIN_TYPE_COMMON);
                }
            }
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        LoginBean bean = BeanFactory.getLoginBean();

        if (loggedIn(request)) {
            // Användare inloggad
            if (request.getServletPath().equals("/logout")) {
                // Användaren kommer till ./logout => Användare loggas ut.
                session.setAttribute("state", LOGIN_FALSE);
                bean.setErrorCode(LOGIN_SING_OUT);
            } else {
                // Användare kommer till ./ eller ./login => omdirigeras till ./dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        } else {
            // Användare ej inloggad
            if (request.getAttribute("login_type") != null) {
                // Användaren vidaresänds från en befintlig inloggningssession.
                // Returnera tillbaka till samma inloggningssession som användaren kom ifrån.
                if ((int) request.getAttribute("login_type") == LOGIN_TYPE_ADMIN) {
                    bean.setAdminLogin(true);
                } else {
                 bean.setAdminLogin(false);
                }
                bean.setErrorCode((int) request.getAttribute("msg_code"));
            } else if (request.getServletPath().equals("/login")) {
                // Användare kommer in till administrationsinloggning
                bean.setAdminLogin(true);
            } else {
                // Användare kommer in till den allmänna inloggningen
                bean.setAdminLogin(false);
            }
        }

        forwardToView(request, response, "/login.jsp",bean);
    }

}
