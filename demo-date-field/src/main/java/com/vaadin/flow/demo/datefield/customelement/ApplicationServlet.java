package com.vaadin.flow.demo.datefield.customelement;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.server.Constants;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;

/***
 * Servlet class for using the original frontend resources of the project.
 */

@WebServlet(urlPatterns = "/*", name = "ApplicationServlet", initParams = {
        @WebInitParam(name = Constants.USE_ORIGINAL_FRONTEND_RESOURCES, value = "true") })
@VaadinServletConfiguration(productionMode = false)
public class ApplicationServlet extends VaadinServlet {
}