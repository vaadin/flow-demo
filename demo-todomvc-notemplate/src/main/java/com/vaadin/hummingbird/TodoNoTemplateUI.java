package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("todomvc")
public class TodoNoTemplateUI extends UI {
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TodoNoTemplateUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        setSizeUndefined();

        CssLayout root = new CssLayout();
        root.setId("root");
        setContent(root);

        TodoViewImpl todoViewImpl = new TodoViewImpl();
        root.addComponent(todoViewImpl);

        HTML info = new HTML("<div><p>Double-click to edit a todo</p>"
                + "<p>Written by <a href=\"https://github.com/jounik\">Jouni Koivuviita</a> and <a href=\"https://github.com/marlonrichert\">Marlon Richert</a></p>"
                + "<p>Part of <a href=\"http://todomvc.com\">TodoMVC</a></p></div>");
        info.setId("info");
        root.addComponent(info);
    }

}