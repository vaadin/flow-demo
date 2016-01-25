package com.vaadin.hummingbird.routing.router;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import com.vaadin.hummingbird.kernel.Element.EventRegistrationHandle;
import com.vaadin.ui.UI;

import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class History {

    public interface PopStateListener {
        void onPopState(PopStateEvent event);
    }

    public static class PopStateEvent extends EventObject {

        private final JsonValue state;

        public PopStateEvent(JsonValue state, History source) {
            super(source);
            this.state = state;
        }

        public JsonValue getState() {
            return state;
        }
    }

    private final UI ui;

    private List<PopStateListener> listeners;

    private EventRegistrationHandle eventRegistrationHandle;

    public History(UI ui) {
        this.ui = ui;
    }

    public final UI getUI() {
        return ui;
    }

    public EventRegistrationHandle addPopStateListener(
            PopStateListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
            addClientSideListener();
        }
        listeners.add(listener);
        return () -> {
            removePopStateListener(listener);
        };
    }

    public void removePopStateListener(PopStateListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                listeners = null;
                removeClientSideListener();
            }
        }
    }

    private void addClientSideListener() {
        ui.addStyleName("history-ui");
        ui.getRootNode().enqueueRpc(
                "var ui = document.getElementsByClassName('history-ui')[0];"
                        + "window.onpopstate = function(e) {"
                        + "var event = new CustomEvent('popstate', {detail: {state: e.state}});"
                        + "ui.dispatchEvent(event);};");
        ui.getElement().addEventData("popstate", "event.detail.state");
        eventRegistrationHandle = ui.getElement().addEventListener("popstate",
                this::handlePopState);
    }

    private void removeClientSideListener() {
        ui.getRootNode().enqueueRpc("window.onpopstate = ''");
        ui.getElement().removeEventData("popstate", "state");
        eventRegistrationHandle.remove();
    }

    private void handlePopState(JsonObject eventData) {
        if (listeners != null && !listeners.isEmpty()) {
            PopStateEvent event = new PopStateEvent(
                    eventData.get("event.detail.state"), this);
            listeners.forEach(l -> l.onPopState(event));
        } else {
            removeClientSideListener();
        }
    }

    public void forward() {
        ui.getRootNode().enqueueRpc("history.forward()");
    }

    public void back() {
        ui.getRootNode().enqueueRpc("history.back()");
    }

    public void back(int distance) {
        ui.getRootNode().enqueueRpc("history.back($0)", distance);
    }

    public void go(int distance) {
        ui.getRootNode().enqueueRpc("history.go($0)", distance);
    }

    public void pushState(Object stateData, String title, String url) {
        if (url != null) {
            ui.getRootNode().enqueueRpc("history.pushState($0, $1, $2)",
                    stateData, title, url);
        } else {
            ui.getRootNode().enqueueRpc("history.pushState($0, $1)", stateData,
                    title);
        }
    }

    public void replaceState(JsonValue stateData, String title, String url) {
        if (url != null) {
            ui.getRootNode().enqueueRpc("history.replaceState($0, $1, $2)",
                    stateData, title, url);
        } else {
            ui.getRootNode().enqueueRpc("history.replaceState($0, $1)",
                    stateData, title);
        }
    }
}
