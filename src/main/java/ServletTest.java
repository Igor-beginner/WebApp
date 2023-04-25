

import java.io.IOException;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import DAO.*;
/**
 * Servlet implementation class ServletTest
 */
@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String parametr = request.getParameter("q");
		boolean isRegistation = parametr == null ? false : parametr.equals("r");
		String page = "/login.html";
		if(isRegistation) {
			page = "/registrator.html";
		}
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);	
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOManagment dao = new DAOPostgreSQLLoginners();
		String email = request.getParameter("email");
		String name = request.getParameter("first-name");
		String second_name = request.getParameter("last-name");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm-password");
		Account account = new Account(0, name, second_name, password, email);
		PrintWriter pw = response.getWriter();
		
		String parametr = request.getParameter("q");
		if (parametr.equals("r")) {
			if(!password.equals(confirmPassword)) {
				pw.println("Passwords aren't equals!");
				return;
			}
			if(!dao.isBusy(account)) {
				try {
					dao.add(account);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pw.println("Your account was enrolled");
			}else {
				pw.println("This email already is busy");
			}
		}else if (parametr.equals("l")) {
			if(dao.autorization(account)) {
				pw.println("Success!");
			}else {
				pw.println("The login or password are wrong");
			}
		}
		
		pw.close();
	}
}