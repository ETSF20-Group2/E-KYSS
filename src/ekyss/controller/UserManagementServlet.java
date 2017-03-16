package ekyss.controller;

import base.servletBase;
import ekyss.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(
        name="UserManagementServlet",
        urlPatterns = {
                "/management/users",
        }
)
public class UserManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;

    protected boolean validateInput(UserManagementBean umb) {
        return true;
    }

    protected String generatePassword() {
        final String ALPHANUMERICS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < 6) {
            int index = (int) (rnd.nextFloat() * ALPHANUMERICS.length());
            password.append(ALPHANUMERICS.charAt(index));
        }
        return password.toString();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManagementBean umb = BeanFactory.getUserManagementBean();
        BeanUtilities.populateBean(umb,request);
        if(validateInput(umb)) {       // TODO: IMPLEMENT validateInput(UserManagementBean)
            String pw = generatePassword();
            umb.setPassword(pw);
            System.out.print(umb    .getUsername());
            MailHandler.sendPassword(umb.getEmail(), pw);
            BeanTransaction.addUser(umb);
        }
        else {
            System.out.print("Error validating bean!");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserManagementBean bean = BeanFactory.getUserManagementBean();
        System.out.println(bean.getAllUsers().toString());
        forwardToView(request, response, "/usermanagement.jsp",bean);

    }

}
