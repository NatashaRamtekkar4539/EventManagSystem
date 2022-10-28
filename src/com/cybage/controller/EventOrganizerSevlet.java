package com.cybage.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cybage.dao.EventOrganizerDao;

@WebServlet("/")
public class EventOrganizerSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EventOrganizerDao userDAO;
	
	public void init() {
		eventOrganizerDao = new EventOrganizerDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertEvent(request, response);
				break;
			case "/delete":
				deleteEvent(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateEvent(request, response);
				break;
			default:
				listEvent(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listEvent(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Event> listEvent = eventOrganizerDao.selectAllEvents();
		request.setAttribute("listEvent", listEvent);
		RequestDispatcher dispatcher = request.getRequestDispatcher("event-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("event-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Event existingEvent = eventOrganizerDao.selectEvent(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("event-form.jsp");
		request.setAttribute("event", existingEvent);
		dispatcher.forward(request, response);

	}

	private void insertEvent(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String venue = request.getParameter("venue");
		String price = request.getParameter("price");
		Event newEvent = new Event(name, email, venue, price);
		eventOrgaizerDao.insertEvent(newEvent);
		response.sendRedirect("list");
	}

	private void updateEvent(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String venue = request.getParameter("venue");
		String price = request.getParameter("price");

		Event book = new Event(id, name, email, venue, price);
		eventOrganizerDao.updateEvent(book);
		response.sendRedirect("list");
	}

	private void deleteEvent(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		eventOrganizerDao.deleteEvent(id);
		response.sendRedirect("list");

	}

}