package com.vaadin.hummingbird.routing.ui;

import java.util.List;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.hummingbird.routing.router.Router;
import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.hummingbird.routing.router.ViewDisplay;
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
    public void show(Router router, View topLevelView) {
        mainMenu.markSelected(topLevelView.getPath());
        if (topLevelView instanceof HasElement) {
            getElementById("main")
                    .appendChild(((HasElement) topLevelView).getElement());
        }

        List<View> secondLevelViews = router
                .getRegisteredSubViews(topLevelView);
        if (!secondLevelViews.isEmpty()) {
            if (subMenu == null) {
                subMenu = new SubMenu();
                getElementById("sub-menu").appendChild(subMenu.getElement());
            }
            subMenu.removeAllItems();
            secondLevelViews
                    .forEach(v -> subMenu.addMenuItem(v.getPath(), v.getPath()));
        } else if (subMenu != null) {
            subMenu.removeAllItems();
            subMenu.getElement().removeFromParent();
            subMenu = null;
        }
    }

    @Override
    public void remove(View view) {
        mainMenu.removeSelected(view.getPath());
    }

}
