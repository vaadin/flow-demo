package com.vaadin.hummingbird.routing.router;

import elemental.json.JsonValue;

public interface View {

    String getPath();

    default void open(JsonValue state, String path) {
    }

    default void show(View subView) {
    };

    default String getParentViewPath() {
        return null;
    }

    // TODO needs to be able to continue navigation if blocked
    default boolean remove() {
        return true;
    };

    default boolean remove(View subView) {
        return true;
    };

}
