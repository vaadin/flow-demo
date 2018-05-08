package com.vaadin.flow.demo.jquerytable.page;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.server.Constants;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;

/***
 * Servlet class for using original frontend resources for the project.
 */

@WebServlet(urlPatterns = "/*", name = "ApplicationServlet", initParams = {
        @WebInitParam(name = Constants.USE_ORIGINAL_FRONTEND_RESOURCES, value = "true") })
@VaadinServletConfiguration(productionMode = true)
public class ApplicationServlet extends VaadinServlet {
}
