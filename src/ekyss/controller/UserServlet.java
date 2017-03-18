package ekyss.controller;

import base.servletBase;
import ekyss.model.*;

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
    private final int ERR_NO_MSG = 0;
    private final int ERR_NOT_MATCHING = 1;
    private final int ERR_WRONG_FORMAT = 2;
    private final int ERR_SAME_AS_OLD = 3;
    private final int ERR_WRONG_PASS = 4;
    private final int SUCCESS = 5;

    private boolean validateNewPass(String pass){
        if(pass.length() == 6){
            for(char c:pass.toCharArray()){
                if((int) c < 97 || (int) c > 122){
                    return false;
                }
            }
           return true;
        }
        return false;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        UserBean bean = BeanFactory.getUserBean((String) session.getAttribute("name"));
        BeanUtilities.populateBean(bean, request);
        if(securityCheck(request)){
                if(!bean.getNewPassword1().equals(bean.getNewPassword2())){
                    bean.setErrorCode(ERR_NOT_MATCHING);
                } else if(!validateNewPass(bean.getNewPassword1())){
                    bean.setErrorCode(ERR_WRONG_FORMAT);
                } else if(bean.getOldPassword().equals(bean.getNewPassword1())){
                    bean.setErrorCode(3);
                } else if(BeanTransaction.changePassword(bean)){
                    bean.setErrorCode(5);
                } else {
                    bean.setErrorCode(4);
                }
                forwardToView(request, response, "/user.jsp", bean);

                return;
        } else{
            response.sendRedirect("/");
        }
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(securityCheck(request)) {
            HttpSession session = request.getSession(true);
            String user = (String) session.getAttribute("name");
            UserBean bean = BeanFactory.getUserBean(user);
            forwardToView(request, response, "/user.jsp", bean);
        }

    }
}
