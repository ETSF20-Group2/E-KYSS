package base;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 *  Denna klass är superklassen för alla servlets i denna applikation.
 */
public class servletBase extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Define states
    protected static final int LOGIN_FALSE = 0;
    protected static final int LOGIN_TRUE = 1;
    protected static final int LOGIN_LOGOUT_TOO_LONG = 2;
    protected static final long MAXINTERVAL = 15; // maxinterval mellan aktivitet på sidan.

    /**
     * Checks if a user is logged in or not.
     * @param request The HTTP Servlet request (so that the session can be found)
     * @return true if the user is logged in, otherwise false.
     */
    protected boolean loggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        int state = LOGIN_FALSE;
        Object objectState = session.getAttribute("state");
        if (objectState != null)
            state = (Integer) objectState;
        return (state == LOGIN_TRUE);
    }

    /**
     * Utility-funktion för att beräkna tidsdifferens mellan första entry till senaste
     * aktivitet/session aktivitet.
     * @param date1 Start tid.
     * @param date2 Slut tid.
     * @param timeUnit Tidsenheter
     * @return tidsdifferensen mellan första enty till senaste aktivitet.
     */
    public static long calcActivityTime(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * Validerar aktiviteten och returnerar sant eller falskt om senaste aktivitet skedde
     * för maxInterval minuter sen.
     * @param session Sessionen.
     * @param maxInterval Max tid för inaktivitet.
     * @return true om senate aktivitet skedde för maxInterval minuter sen.
     */
    protected boolean validateActivity(HttpSession session, long maxInterval) {
        Date createTime = new Date(session.getCreationTime());
        if(session.isNew()) {
            // Get last access time of this web page.
            Date lastAccessTime =
                    new Date(session.getLastAccessedTime());
            return true;
        }
        else {
            long difftime = 0;
            Date lastAccessTime = new Date(session.getLastAccessedTime());
            return(TimeUnit.MINUTES.convert(difftime, TimeUnit.SECONDS) < maxInterval);
        }
    }

    /**
     * Vidaresänder böna till en given vy-nivå (JSP).
     * @param request HttpServletRequesten
     * @param response HttpServletResponsen
     * @param patternToJSP Vägen till JSP sidan.
     * @param bean En böna av vilken typ som helst med värden jsp-sidan kan behöva.
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String patternToJSP, Object bean) throws ServletException, IOException {
        if(!validateActivity(request.getSession(), MAXINTERVAL)) {
            response.sendRedirect(request.getContextPath() + "/logout");
            request.getSession().invalidate(); // invalidera sessionen.
            return;
        }
        request.setAttribute("bean", bean);
        forwardToView(request, response, patternToJSP);
    }

    /**
     * Vidaresänder till given vy-nivå (JSP).
     * @param request HttpServletRequesten
     * @param response HttpServletResponsen
     * @param patternToJSP Vägen till JSP sidan.
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String patternToJSP) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(patternToJSP);
        rd.forward(request, response);


    }

    /**
     * Kollar om användaren är inloggad och har behörighet att visa sidan.
     * ((( Vad jag kan se är det bara dessa servlets som behöver någon specialbehandling beroende på roll )))
     * @param request HttpServletRequest
     * @return true om användaren har behörighet, annars false.
     */
    protected boolean securityCheck(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("name");

        if (username != null && loggedIn(request)) {
            boolean isPL = (boolean) session.getAttribute("ProjectLeader");
            String path = request.getServletPath();
            switch (path) {
                case "/dashboard":
                    return true;
                case "/management/groups":
                    return username.equals("admin");
                case "/management/reports":
                    return isPL;
                case "/report":
                    return !username.equals("admin");
                case "/management/users":
                    return username.equals("admin") || isPL;
                case "/settings/user":
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }
}