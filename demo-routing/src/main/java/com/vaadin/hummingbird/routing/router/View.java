package com.vaadin.hummingbird.routing.router;

import com.vaadin.ui.Component;

import elemental.json.JsonValue;

public interface View {

    default void open(JsonValue state, String path) {
    }

    default void show(View subView) {
    };

    // TODO needs to be able to continue navigation if blocked
    default boolean remove() {
        return true;
    };

    default boolean remove(View subView) {
        return true;
    };

    default Component getComponent() {
        if (this instanceof Component) {
            return (Component) this;
        } else {
            throw new IllegalStateException(
                    "View not a component and getComponent() not overridden for: "
                            + getClass().getName());
        }
    }

    default String getPath() {
        return getPath(this);
    };

    default String getParentViewPath() {
        return getParentViewPath(this);
    }

    static String getPath(Object o) {
        Route route;
        if (o instanceof Class) {
            route = ((Class<?>) o).getAnnotation(Route.class);
        } else {
            route = o.getClass().getAnnotation(Route.class);
        }
        if (route == null) {
            throw new IllegalStateException("No @Route defined for "
                    + (o instanceof Class ? ((Class<?>) o).getName()
                            : o.getClass().getName()));
        }
        return route.path();
    };

    static String getParentViewPath(Object o) {
        Route route;
        if (o instanceof Class) {
            route = ((Class<?>) o).getAnnotation(Route.class);
        } else {
            route = o.getClass().getAnnotation(Route.class);
        }
        if (route == null) {
            return null;
        } else {
            String parentPath = route.parentPath();
            return parentPath.isEmpty() ? null : parentPath;
        }
    }
}
