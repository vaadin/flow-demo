package com.vaadin.hummingbird.routing.ui.view;

import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.hummingbird.routing.ui.SubMenu;

public abstract class AbstractSubMenuView extends AbstractView {

    private SubMenu subMenu;

    public void setSubMenu(SubMenu subMenu) {
        this.subMenu = subMenu;
    }

    @Override
    public void show(View subView, String subViewPath) {
        super.show(subView, subViewPath);
        subMenu.markSelected("/" + subViewPath);
    }

    @Override
    public boolean remove(View subView, String subViewPath) {
        super.remove(subView, subViewPath);
        subMenu.removeSelected();
        return true;
    };

    @Override
    public boolean remove() {
        super.remove();
        subMenu = null;
        return true;
    }

    public abstract String[][] getSubMenuLinks();
}
