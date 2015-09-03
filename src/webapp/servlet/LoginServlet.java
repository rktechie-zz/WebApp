package webapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webapp.dao.UserDAO;
import webapp.model.UserBean;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	
	
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		} catch(Exception e){
			e.printStackTrace();
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			UserBean userBean = new UserBean();
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			UserDAO userDao = new UserDAO();
			userBean = userDao.login(email, password);
			if(userBean != null){
				session = request.getSession();
				session.setAttribute("email", userBean.getEmail());
				session.setAttribute("first_name", userBean.getFirstName());
				session.setAttribute("last_name", userBean.getLastName());
				session.setAttribute("login", "valid");
				RequestDispatcher dispatcher = request.getRequestDispatcher("bookmark.jsp");
				dispatcher.forward(request, response);
			}else{
				request.setAttribute("email", email);
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
