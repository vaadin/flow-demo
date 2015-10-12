package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.UI;

@HTML("vaadin://bower_components/google-map/google-map.html")
@Theme("simplecrm")
@Title("Simple CRM v1")
public class CrmUI extends UI {
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = CrmUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		// Redirect to correct template based on the request URL
		String pathInfo = request.getPathInfo();
		AbstractComponent content = null;
		if (pathInfo != null) {
			switch (pathInfo) {
			case "/Customers": {
				content = null;
				break;
			}
			case "/Map": {
				content = null;
				break;

			}
			case "/Analyze": {
				content = null;
				break;
			}
			case "/About": {
				content = new About();
				break;
			}
			default: {
				content = new About();
				break;
			}
			}
		}
		NavView nav = new NavView(content);
		setContent(nav);
	}

}