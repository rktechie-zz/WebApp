package webapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webapp.dao.BookmarkDAO;
import webapp.dao.UserDAO;
import webapp.model.BookmarkBean;
import webapp.model.UserBean;

public class BookmarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
	
	public BookmarkServlet() {
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
		try {
			BookmarkDAO bookmarkDAO = new BookmarkDAO();
			BookmarkBean bookmarkBean = new BookmarkBean();
			String action = request.getParameter("action");
			if (action.equals("getUserBookmarks")) {
				String email = request.getParameter("email");
				bookmarkBean.setEmail(email);
				List<BookmarkBean> listBookmarkBeans = bookmarkDAO.getUserBookmarks(bookmarkBean);
				request.setAttribute("listBookmarkBeans", listBookmarkBeans);
				RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
				dispatcher.forward(request, response);
			} else{
				response.sendRedirect("error.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			BookmarkBean bookmarkBean = new BookmarkBean();
			BookmarkDAO bookmarkDAO = new BookmarkDAO();
			String action = request.getParameter("action");
			session = request.getSession();
			String email;
			String url;
			String comment;
			List<BookmarkBean> listBookmarkBeans;
			RequestDispatcher dispatcher;
			switch(action){
				case "addBookmark":
					if((String)session.getAttribute("login") == "valid"){
						email = (String) request.getSession().getAttribute("email");
						url = request.getParameter("url");
						comment = request.getParameter("comment");
						bookmarkBean.setEmail(email);
						bookmarkBean.setUrl(url);
						bookmarkBean.setComment(comment);
						bookmarkDAO.create(bookmarkBean);
						listBookmarkBeans = bookmarkDAO.getUserBookmarks(bookmarkBean);
						request.setAttribute("listBookmarkBeans", listBookmarkBeans);
						dispatcher = request.getRequestDispatcher("list.jsp");
						dispatcher.forward(request, response);
					} else{
						response.sendRedirect("error.jsp");
					}
					break;
				case "incrementCount":
					email = request.getParameter("email");
					url = request.getParameter("url");
					comment = request.getParameter("comment");
					bookmarkBean.setEmail(email);
					bookmarkBean.setUrl(url);
					bookmarkBean.setComment(comment);
					bookmarkDAO.update(bookmarkBean);
					response.getWriter().write("true");
					break;
				case "manageBookmarks":
					if((String)session.getAttribute("login") == "valid"){
						String sessionEmail = (String) session.getAttribute("email");
						String formEmail = request.getParameter("email");
						if (sessionEmail.equals(formEmail)) {
							bookmarkBean.setEmail(sessionEmail);
							listBookmarkBeans = bookmarkDAO.getUserBookmarks(bookmarkBean);
							request.setAttribute("listBookmarkBeans", listBookmarkBeans);
							dispatcher = request.getRequestDispatcher("manageBookmarks.jsp");
							dispatcher.forward(request, response);
						} else {
							response.sendRedirect("error.jsp");
						}
					} else{
						response.sendRedirect("error.jsp");
					}
					break;
				case "deleteBookmark":
					if((String)session.getAttribute("login") == "valid"){
						email = request.getParameter("email");
						url = request.getParameter("url");
						comment = request.getParameter("comment");
						bookmarkBean.setEmail(email);
						bookmarkBean.setUrl(url);
						bookmarkBean.setComment(comment);
						bookmarkDAO.delete(bookmarkBean);
						response.getWriter().write("true");
					} else{
						response.getWriter().write("false");
					}
					break;
				default:
					response.sendRedirect("error.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
