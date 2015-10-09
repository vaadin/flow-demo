package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("simplecrm")
@Title("Simple CRM v1")
public class CrmUI extends UI {
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = CrmUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		// Create a template of each of the pages
		Customers customers = new Customers();
		Map map = new Map();
		Analyze analyze = new Analyze();
		About about = new About();

		// Redirect to correct template based on the request URL
		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			switch (pathInfo) {
			case "/Customers": {
				setContent(customers);
				break;
			}
			case "/Map": {
				setContent(map);
				break;

			}
			case "/Analyze": {
				setContent(analyze);
				break;
			}
			case "/About": {
				setContent(about);
				break;
			}
			default: {
				setContent(customers);
				break;
			}
			}
		}
	}

}