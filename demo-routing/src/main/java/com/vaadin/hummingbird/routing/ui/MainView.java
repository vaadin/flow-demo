package com.vaadin.hummingbird.routing.ui;

import java.util.stream.Stream;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.hummingbird.routing.router.ViewDisplay;
import com.vaadin.hummingbird.routing.ui.view.AbstractSubMenuView;
import com.vaadin.ui.HasElement;
import com.vaadin.ui.Template;

@com.vaadin.annotations.HTML({
        "context://bower_components/promise-polyfill/promise-polyfill-lite.html" })
@StyleSheet(value = "vaadin://main.css")
public class MainView extends Template implements ViewDisplay {

    private final MainMenu mainMenu;
    private SubMenu subMenu;

    public MainView() {
        mainMenu = new MainMenu();
        subMenu = null;
        getElementById("main-menu").appendChild(mainMenu.getElement());

        mainMenu.addMenuItem("Framework", "framework");
        mainMenu.addMenuItem("Elements", "elements");
        mainMenu.addMenuItem("Pro Tools", "pro-tools");
        mainMenu.addMenuItem("Download", "download");
        mainMenu.addMenuItem("Community", "community");
        mainMenu.addMenuItem("Services", "services");
        mainMenu.addMenuItem("Vaadin Pro", "pro-oldpage");
    }

    @Override
    public void show(View topLevelView) {
        mainMenu.markSelected(topLevelView.getPath());
        if (topLevelView instanceof HasElement) {
            getElementById("main")
                    .appendChild(((HasElement) topLevelView).getElement());
        }

        if (topLevelView instanceof AbstractSubMenuView) {
            if (subMenu == null) {
                subMenu = new SubMenu();
                getElementById("sub-menu").appendChild(subMenu.getElement());
            }
            subMenu.removeAllItems();
            Stream.of(((AbstractSubMenuView) topLevelView).getSubMenuLinks())
                    .forEach(s -> subMenu.addMenuItem(s[0], s[1]));
            ((AbstractSubMenuView) topLevelView).setSubMenu(subMenu);
        } else if (subMenu != null) {
            subMenu.removeAllItems();
            subMenu.getElement().removeFromParent();
            subMenu = null;
        }
    }

    @Override
    public void remove(View view) {
        mainMenu.removeSelected();
    }

}
