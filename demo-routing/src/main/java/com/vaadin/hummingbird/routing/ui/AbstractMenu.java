package com.vaadin.hummingbird.routing.ui;

import java.util.List;

import com.vaadin.ui.Template;

public abstract class AbstractMenu extends Template {

    public interface MenuItem {
        String getTitle();

        void setTitle(String title);

        String getPath();

        void setPath(String path);

        boolean isSelected();

        void setSelected(boolean selected);
    }

    public interface MenuModel extends Model {
        List<MenuItem> getItems();

        void setItems(List<MenuItem> items);
    }

    @Override
    protected MenuModel getModel() {
        return (MenuModel) super.getModel();
    }

    public AbstractMenu addMenuItem(String title, String path) {
        MenuItem item = Model.create(MenuItem.class);
        item.setTitle(title);
        item.setPath(path);
        item.setSelected(false);
        getModel().getItems().add(item);
        return this;
    }

    public AbstractMenu removeAllItems() {
        getModel().getItems().clear();
        return this;
    }

    public void markSelected(String path) {
        getModel().getItems().forEach(item -> {
            String itemPath = item.getPath();
            if (itemPath.startsWith(path)) {
                item.setSelected(true);
            } else {
                item.setSelected(false);
            }
        });
    }

    public void removeSelected() {
        getModel().getItems().forEach(item -> item.setSelected(false));
    }
}
