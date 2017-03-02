package ekyss.controller;

import base.LogIn;
import ekyss.model.BeanUtilities;
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
public class LoginServlet extends LogIn {

    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        LoginBean bean = new LoginBean();
        BeanUtilities.populateBean(bean, request);

        // --- START AV TEST ----
        // Test om informationen kommer in till bönan från POST-anropet
        System.out.println("USERNAME: " + bean.getUsername());
        System.out.println("PASSWORD: " + bean.getPassword());
        System.out.println("GROUP: " + bean.getGroup());
        // Skicka tillbaka till login
        doGet(request, response);
        // --- SLUT PÅ TEST ----
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        LoginBean bean = new LoginBean();

        // --- START AV TEST ----
        // Test för JSP innan modellen implementeras.
        // Sätter in test för projektgrupp
        bean.setGroups("AAA");
        bean.setGroups("BBB");
        bean.setGroups("CCC");
        bean.setGroups("DDD");
        // Sätter test för felmeddelande
        bean.setErrorCode(1);
        request.setAttribute("bean", bean);
        // ---- SLUT PÅ TEST ----

        forwardToView(request, response, "/login.jsp", bean);
    }

}
