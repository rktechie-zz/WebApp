package webapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webapp.dao.BookmarkDAO;
import webapp.dao.UserDAO;
import webapp.model.UserBean;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;

	public UserServlet() {
		super();
	}

	public void init() throws ServletException {
		try {
			UserDAO userDAO = new UserDAO();
			userDAO.initializeTables();
			BookmarkDAO bookmarkDAO = new BookmarkDAO();
			bookmarkDAO.initializeTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			if (action.equals("getAllUsers")) {
				UserDAO userDAO = new UserDAO();
				List<UserBean> allUsers = userDAO.getAllUsers();
				request.setAttribute("allUsers", allUsers);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String action = request.getParameter("action");
			UserDAO userDAO = new UserDAO();
			UserBean userBean = new UserBean();
			String email;
			String password;
			String firstName;
			String lastName;
			session = request.getSession();
			switch (action) {
				case "checkEmail":
					email = request.getParameter("email");
					password = request.getParameter("password");
					userBean = userDAO.lookup(email);
					if (userBean != null) {
						response.getWriter().write("true");
					} else {
						session.setAttribute("email", email);
						session.setAttribute("password", password);
						response.getWriter().write("false");
					}
					break;
				case "details":
					email = (String) session.getAttribute("email");
					password = (String) session.getAttribute("password");
					firstName = request.getParameter("first_name");
					lastName = request.getParameter("last_name");
					userBean.setEmail(email);
					userBean.setFirstName(firstName);
					userBean.setLastName(lastName);
					userBean.setPassword(password);
					userDAO.create(userBean);
					session.setAttribute("first_name", firstName);
					session.setAttribute("last_name", lastName);
					session.removeAttribute("password");
					session.setAttribute("login", "valid");
					RequestDispatcher dispatcher = request.getRequestDispatcher("bookmark.jsp");
					dispatcher.forward(request, response);
					break;
				case "changePassword":
					if((String)session.getAttribute("login") == "valid"){
						email = (String) session.getAttribute("email");
						password = request.getParameter("password");
						userBean.setEmail(email);
						userBean.setPassword(password);
						userDAO.changePassword(userBean);
						response.sendRedirect("index.jsp");
					} else{
						response.sendRedirect("error.jsp");
					}
					break;
				case "logout":
					session.invalidate();
					response.sendRedirect("index.jsp");
					break;
				case "login":
					email = request.getParameter("email");
					password = request.getParameter("password");
					userBean = userDAO.login(email, password);
					if (userBean != null) {
						session.setAttribute("email", userBean.getEmail());
						session.setAttribute("first_name", userBean.getFirstName());
						session.setAttribute("last_name", userBean.getLastName());
						session.setAttribute("login", "valid");
						response.getWriter().write("true");
					} else {
						request.setAttribute("email", email);
						response.getWriter().write("false");
					}
					break;
				case "getAllUsers":
					List<UserBean> allUsers = userDAO.getAllUsers();
					request.setAttribute("allUsers", allUsers);
					break;
				default:
					response.sendRedirect("error.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

}
