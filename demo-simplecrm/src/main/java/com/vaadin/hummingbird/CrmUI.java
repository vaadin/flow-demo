package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@Theme("simplecrm")
@Title("Hummingbird Simple CRM")
public class CrmUI extends UI {
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = CrmUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		SimpleCrmMain main = new SimpleCrmMain();
		setContent(main);
	}

}