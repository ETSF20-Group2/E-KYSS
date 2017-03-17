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
    private static final long MAXPASSWORDLENGTH = 6;

    private static final int ACTION_ADDUSER = 1;
    private static final int ACTION_ASSIGNROLE = 2;
    private static final int ACTION_ASSIGNGROUP = 3;

    protected String generatePassword() {
        final String ALPHANUMERICS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < MAXPASSWORDLENGTH) {
            int index = (int) (rnd.nextFloat() * ALPHANUMERICS.length());
            password.append(ALPHANUMERICS.charAt(index));
        }
        return password.toString();
    }

    protected boolean validateInput(UserManagementBean umb)
    {
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManagementBean umb = BeanFactory.getUserManagementBean();
        BeanUtilities.populateBean(umb,request);
        if(validateInput(umb)) { // TODO: IMPLEMENT validateInput(UserManagementBean)
            String pw = generatePassword();
            umb.setPassword(pw);
            System.out.print(umb.getUsername());
            MailHandler.sendPassword(umb.getEmail(), pw);
            BeanTransaction.addUser(umb);
        }
        else {
            System.out.print("Error validating bean!");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // hantering av flera path gets, dvs om man ska t.ex. tilldela roll / grupp istället.
        UserManagementBean bean = BeanFactory.getUserManagementBean();
        forwardToView(request, response, "/usermanagement.jsp",bean);
    }

}
