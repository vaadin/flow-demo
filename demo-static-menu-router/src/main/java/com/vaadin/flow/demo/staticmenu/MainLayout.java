package com.vaadin.flow.demo.staticmenu;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.router.RouterLayout;

@StyleSheet("css/site.css")
public class MainLayout extends Div implements RouterLayout {

    private MainMenuBar menu = new MainMenu();

    public MainLayout() {
        add(menu);
    }
}
