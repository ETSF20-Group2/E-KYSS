package ekyss.controller;

import base.servletBase;
import ekyss.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.*;

@WebServlet(
        name="UserManagementServlet",
        urlPatterns = {
                "/management/users",
        }
)
/**
 *  Denna klass är servlet för webbsidan "Användarhantering".
 */
public class UserManagementServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_CREATE = "add";
    private final String TYPE_DELETE = "delete";
    private final String TYPE_ASSIGN = "assign";
    private static final long MAXPASSWORDLENGTH = 6;

    private final int ERR_ASSIGNED = 1;
    private final int ERR_NOTASSIGNED = 2;
    private final int ERR_CREATED = 3;
    private final int ERR_NAMETOOLONG = 4;
    private final int ERR_NAMETOOSHORT = 5;
    private final int ERR_NAMEINVALID = 6;
    private final int ERR_DELETED = 7;
    private final int ERR_DELETEERROR = 8;
    private final int ERR_DELETENOTSELECTED = 9;
    private final int ERR_INVALIDEMAIL = 10;

    private int err_code = 0;


    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String group = (String) session.getAttribute("group");
            UserManagementBean umb = BeanFactory.getUserManagementBean(group);
            BeanUtilities.populateBean(umb,request);
            if(umb.getType().equals(TYPE_CREATE)) {
                if(validateEmail(umb.getEmail())) {
                    if (validateInput(umb)) {       // TODO: IMPLEMENT validateInput(UserManagementBean)
                        String pw = generatePassword();
                        umb.setPassword(pw);
                        MailHandler.sendPassword(umb.getEmail(), pw);
                        err_code = ERR_CREATED;
                        BeanTransaction.addUser(umb);
                    } else {
                        if (umb.getUsername().length() < 5) {
                            err_code = ERR_NAMETOOSHORT;
                        } else if (umb.getUsername().length() > 10) {
                            err_code = ERR_NAMETOOLONG;
                        } else {
                            err_code = ERR_NAMEINVALID;
                        }
                    }
                } else {
                    err_code = ERR_INVALIDEMAIL;
                }
            }
            else if(umb.getType().equals(TYPE_DELETE)){
                if(umb.getDeleteUserList() != null) {
                    if (BeanTransaction.deleteUsers(umb.getDeleteUserList())) {
                        err_code = ERR_DELETED;
                    } else {
                        err_code = ERR_DELETEERROR;
                    }
                } else {
                    err_code = ERR_DELETENOTSELECTED;
                }
            }
            else if(umb.getType().equals(TYPE_ASSIGN)){
                if(BeanTransaction.assignRoles(group, umb.getAssignRole())) {
                    err_code = ERR_ASSIGNED;
                } else {
                    err_code = ERR_NOTASSIGNED;
                }

            }
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect(request.getContextPath() + "/");
        }
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityCheck(request)) {
            // Användaren är inloggad och har behörighet
            HttpSession session = request.getSession();
            String group = (String) session.getAttribute("group");
            UserManagementBean bean = BeanFactory.getUserManagementBean(group);
            bean.setErr_code(err_code);
            forwardToView(request, response, "/usermanagement.jsp",bean);
            err_code = 0;
        } else {
            // Användaren är ej inloggad eller användaren har ej behörighet
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}