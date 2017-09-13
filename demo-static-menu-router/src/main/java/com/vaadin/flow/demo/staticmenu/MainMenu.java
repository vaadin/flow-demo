package com.vaadin.flow.demo.staticmenu;

import com.vaadin.flow.demo.staticmenu.download.DownloadView;
import com.vaadin.flow.demo.staticmenu.elements.ElementsView;
import com.vaadin.flow.demo.staticmenu.framework.FrameworkView;
import com.vaadin.flow.html.Anchor;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.html.HtmlContainer;

/**
 * Main menu bar containing top level navigation items
 */
public class MainMenu extends MainMenuBar {

    private HtmlContainer ul;
    private Anchor homeLink;

    @Override
    public void init() {
        initHomeLink();
        initLinkContainer();
    }

    private void initHomeLink() {
        homeLink = new Anchor("/", "");
        homeLink.getElement().setAttribute("router-link", "true");
        Div logo = new Div();
        logo.setClassName("logo");
        homeLink.add(logo);
        add(homeLink);
    }

    private void initLinkContainer() {
        ul = new HtmlContainer("ul");
        ul.setClassName("topnav");
        add(ul);

        ul.add(createLink(FrameworkView.class, "Framework"));
        ul.add(createLink(ElementsView.class, "Elements"));
        ul.add(createLink(DownloadView.class, "Download"));
    }

}
