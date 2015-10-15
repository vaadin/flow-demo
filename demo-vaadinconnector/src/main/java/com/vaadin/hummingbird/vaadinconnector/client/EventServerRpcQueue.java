package com.vaadin.hummingbird.vaadinconnector.client;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.communication.JsonEncoder;
import com.vaadin.client.communication.ServerRpcQueue;
import com.vaadin.client.metadata.Method;
import com.vaadin.client.metadata.NoDataException;
import com.vaadin.client.metadata.Type;
import com.vaadin.shared.communication.MethodInvocation;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonValue;

public class EventServerRpcQueue extends ServerRpcQueue {
    private Element element;

    public void setTargetElement(Element element) {
        this.element = element;
    }

    @Override
    public void add(MethodInvocation invocation, boolean lastOnly) {
        assert element != null : "EventServerRpcQueue not initialized";

        Type[] parameterTypes = null;
        try {
            Type type = new Type(invocation.getInterfaceName(), null);
            Method method = type.getMethod(invocation.getMethodName());
            parameterTypes = method.getParameterTypes();
        } catch (NoDataException e) {
            throw new RuntimeException(
                    "No type data for " + invocation.toString(), e);
        }

        JsonArray arguments = Json.createArray();

        for (int i = 0; i < invocation.getParameters().length; ++i) {
            Type type = parameterTypes[i];
            Object value = invocation.getParameters()[i];
            JsonValue jsonValue = JsonEncoder.encode(value, type, connection);
            arguments.set(i, jsonValue);
        }

        RpcEvent event = RpcEvent.create(invocation.getInterfaceName(),
                invocation.getMethodName(), arguments);

        element.dispatchEvent(event);
    }

    @Override
    public void flush() {
        // nop
    }
}
