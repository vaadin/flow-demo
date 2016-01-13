package com.vaadin.hummingbird.vaadinconnector.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.vaadin.client.Util;

import elemental.json.JsonArray;
import elemental.json.JsonObject;

public class RpcEvent extends NativeEvent {
    protected RpcEvent() {
        // JSO ctor
    }

    public static RpcEvent create(String iface, String methodName,
            JsonArray arguments) {
        RpcEvent event = Document.get().createHtmlEvent("rpc", false, false)
                .cast();

        JsonObject eventJson = Util.jso2json(event);

        eventJson.put("interface", iface);
        eventJson.put("method", methodName);
        eventJson.put("arguments", arguments);

        return event;
    }

}
