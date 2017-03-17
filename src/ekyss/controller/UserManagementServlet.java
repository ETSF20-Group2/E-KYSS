package ekyss.controller;

import base.servletBase;
import ekyss.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
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

    private final String TYPE_CREATE = "add";
    private final String TYPE_DELETE = "delete";

    protected boolean validateInput(UserManagementBean umb) {
        if (umb.getUsername().length() >= 5 && umb.getUsername().length() <= 10) {
            for(char c : umb.getUsername().toCharArray()){
                int i = (int) c;
                if(i < 48 || i > 122 || i > 57 && i < 65 || i > 90 && i < 97){
                    return false;
                }
            }
            return true;
        }
        return false;
    }


    protected String generatePassword() {
        final String ALPHANUMERICS = "abcdefghijklmnopqrstuvwxy"; // Krav 6.2.3 - ASCII 97-122 = a-z (små bokstäver)
        //final String ALPHANUMERICS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
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
        if(true) {
            UserManagementBean umb = BeanFactory.getUserManagementBean();
            BeanUtilities.populateBean(umb,request);
            System.out.println("Type = " + umb.getType());
            if(umb.getType().equals(TYPE_CREATE)) {
                if (validateInput(umb)) {       // TODO: IMPLEMENT validateInput(UserManagementBean)
                    String pw = generatePassword();
                    umb.setPassword(pw);
                    System.out.print(umb.getUsername());
                    MailHandler.sendPassword(umb.getEmail(), pw);
                    BeanTransaction.addUser(umb);
                } else {
                    System.out.print("Error validating bean!");
                }
            }
            else if(umb.getType().equals(TYPE_DELETE)){
                System.out.println("Type = " + umb.getType());
                BeanTransaction.deleteUsers(umb.getDeleteUserList());
                System.out.println("Deleted user: " + Arrays.toString(umb.getDeleteUserList()));
            }
        } else {

            System.out.println("sendRedirect");
            response.sendRedirect("/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("doGet");
        UserManagementBean bean = BeanFactory.getUserManagementBean();
        System.out.println("bean created");
        System.out.println(bean.getAllUsers().toString());
        forwardToView(request, response, "/usermanagement.jsp",bean);
        System.out.println("forwarded to view");
    }

}
