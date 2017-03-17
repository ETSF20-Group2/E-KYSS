package ekyss.controller;

import base.servletBase;
import ekyss.model.BeanTransaction;
import ekyss.model.BeanUtilities;
import ekyss.model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        name="UserServlet",
        urlPatterns = {
                "/settings/user",
        }
)
public class UserServlet extends servletBase {

    private static final long serialVersionUID = 1L;
    private final String TYPE_CHANGEINFO = "changeInfo";
    private final String TYPE_CHANGEPASS = "changePass";

    private boolean validateNewPass(String pass1, String pass2){
        if(pass1.equals(pass2) && pass1.length() == 6){
            for(char c:pass1.toCharArray()){
                if((int) c < 97 || (int) c > 122){
                    return false;
                }
            }
           return true;
        }
        return false;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        UserBean ub = new UserBean();
        BeanUtilities.populateBean(ub, request);
        HttpSession session = request.getSession(true);
        ub.setUserName((String) session.getAttribute("name"));
        if(true){
            if(ub.getType().equals(TYPE_CHANGEPASS)){
                if(validateNewPass(ub.getNewPassword1(), ub.getNewPassword2())) {
                    BeanTransaction.changePassword(ub);
                }
                // Kommer behövas errorcodes här, t ex om lösen är för långt etc.
            }

        } else{
            response.sendRedirect("/");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(true) {
            UserBean bean = new UserBean();
            forwardToView(request, response, "/user.jsp", bean);
        }

    }
}
