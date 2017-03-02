package base;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *  This class is the superclass for all servlets in the application. 
 *  It includes basic functionality required by many servlets, like for example a page head 
 *  written by all servlets, and the connection to the database. 
 *  
 *  This application requires a database.
 *  For username and password, see the constructor in this class.
 *  
 *  <p>The database can be created with the following SQL command: 
 *  mysql> create database base;
 *  <p>The required table can be created with created with:
 *  mysql> create table users(name varchar(10), password varchar(10), primary key (name));
 *  <p>The administrator can be added with:
 *  mysql> insert into users (name, password) values('admin', 'adminp'); 
 *  
 *  @author Martin Host
 *  @version 1.0
 *  
 */
public class servletBase extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	// Define states
	protected static final int LOGIN_FALSE = 0;
	protected static final int LOGIN_TRUE = 1;	
	//protected Connection conn = null;
	
	/**
	 * Constructs a servlet and makes a connection to the database. 
	 * It also writes all user names on the console for test purpose. 
	 */
    /*
    public servletBase() {
    	try{
			conn = DriverManager.getConnection("jdbc:mysql://localhost/base?" +
            "user=martin&password=");			
			       
						
			// Display the contents of the database in the console. 
			// This should be removed in the final version
			Statement stmt = conn.createStatement();		    
		    ResultSet rs = stmt.executeQuery("select * from users"); 
		    while (rs.next( )) {
		    	String name = rs.getString("name"); 
		    	System.out.println("base " + name);
		    	}

		    stmt.close();
			
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
    }
    */
    
    /**
     * Checks if a user is logged in or not.
     * @param request The HTTP Servlet request (so that the session can be found)
     * @return true if the user is logged in, otherwise false.
     */
    protected boolean loggedIn(HttpServletRequest request) {
    	HttpSession session = request.getSession(true);
    	Object objectState = session.getAttribute("state");
    	int state = LOGIN_FALSE;
		if (objectState != null) 
			state = (Integer) objectState; 
		return (state == LOGIN_TRUE);
    }

    /**
     * Genererar en sida av typen text/plain med kritisk felmeddelande,
     * där denan metod endast används i BaseBlockSystem när klienten
     * skulle råka komma åt BaseBlockSystem inifrån E-KYSS.
     * @param response Tar emot aktuell svaranrop.
     * @throws IOException Kastar ett undantag vid
     */
    protected void criticalErrorMessage(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/plain");
        response.setHeader("success", "yes");
        PrintWriter writer = response.getWriter();
        writer.write("A critical error has occured. Please go back and try again.\n");
        writer.close();
    }

    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String patternToJSP, Object bean) throws ServletException, IOException {
        request.setAttribute("bean", bean);
        forwardToView(request, response, patternToJSP);
    }

    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String patternToJSP) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(patternToJSP);
        rd.forward(request, response);
    }
    
    /**
     * Can be used to construct form elements.
     * @param par Input string
     * @return output string = "par" 
     */
    /*
    protected String formElement(String par) {
    	return '"' + par + '"';
    }
    */
    
    
    /**
     * Constructs the header of all servlets. 
     * @return String with html code for the header. 
     */
    /*
    protected String getPageIntro() {
    	String intro = "<html>" +
                       "<head><title> The Base Block System </title></head>" +
                       "<body>";
    	return intro;
    }
    */

}
