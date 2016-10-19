package com.vaadin.hummingbird.vaadinconnector.client;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.JsArrayObject;
import com.vaadin.client.LayoutManager;
import com.vaadin.client.Util;
import com.vaadin.client.communication.JsonDecoder;
import com.vaadin.client.communication.JsonEncoder;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.metadata.Method;
import com.vaadin.client.metadata.NoDataException;
import com.vaadin.client.metadata.Property;
import com.vaadin.client.metadata.Type;
import com.vaadin.client.metadata.TypeData;
import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.communication.ClientRpc;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonException;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class VaadinConnector extends Element {
    public static final boolean debug = false;

    private static final String parentConnectorId = "1";
    private static final String connectorId = "2";

    private class PropertyHandler {
        private String name;

        public PropertyHandler(String name) {
            this.name = name;
        }

        public void set(JsonValue value) {
            updateState(name, value);
        }

        public JsonValue get() {
            return readState(name);
        }
    }

    protected VaadinConnector() {
        // JSNI CTOR
    }

    public final void createdCallback() {
        log("Created!");
        log(this);
    }

    public final void attachedCallback() {
        ComponentConnector connector = getConnector();
        if (connector == null) {
            try {
                connector = createConnector();
            } catch (NoDataException e) {
                log(e);
                return;
            }
        }

        findFakeParent().setChild(connector);

        connector.fireEvent(new StateChangeEvent(connector, null, true));
    }

    private ComponentConnector createConnector() throws NoDataException {
        String connectorClass = getAttribute("connector");
        ApplicationConnection connection = new ApplicationConnection();
        ((EventServerRpcQueue) connection.getServerRpcQueue())
                .setTargetElement(this);

        ConnectorMap connectorMap = getConnectorMap(connection);

        LayoutManager.get(connection).setConnection(connection);
        FakeParentConnector fakeParent = new FakeParentConnector(this);
        connectorMap.registerConnector(parentConnectorId, fakeParent);
        fakeParent.doInit(parentConnectorId, connection);
        Type type = TypeData.getType(TypeData.getClass(connectorClass));
        ComponentConnector connector = (ComponentConnector) type
                .createInstance();
        setConnector(connector);

        connectorMap.registerConnector(connectorId, connector);
        connector.doInit(connectorId, connection);

        /*
         * Must do this before adding property listeners, since otherwise
         * everything would seem to be defined even though it isn't
         */
        updateFullState(connector);

        addStatePropertyListeners(getStateType(connector));

        attachRpcHandler(this);

        return connector;
    }

    private static native void attachRpcHandler(VaadinConnector self)
    /*-{
        self.rpc = function(iface, method, arguments) {
          @VaadinConnector::handleRpc(*)(self, iface, method, arguments);
        };
    }-*/;

    private static void handleRpc(VaadinConnector self, String iface,
            String methodName, JsonArray argumentsJson) {
        ComponentConnector connector = self.getConnector();

        try {
            Method method = new Type(iface, null).getMethod(methodName);

            Type[] parameterTypes = method.getParameterTypes();
            Object[] arguments = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                arguments[i] = JsonDecoder.decodeValue(parameterTypes[i],
                        argumentsJson.get(i), null, connector.getConnection());
            }

            for (ClientRpc target : connector.getRpcImplementations(iface)) {
                method.invoke(target, arguments);
            }
        } catch (NoDataException e) {
            log(iface + "." + methodName);
            log(e);
        }
    }

    private void addStatePropertyListeners(Type stateType)
            throws NoDataException {
        JsArrayObject<Property> properties = stateType.getPropertiesAsArray();
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            String name = property.getName();

            addPropertyHandler(this, name, new PropertyHandler(name));

        }
    }

    private native static void addPropertyHandler(VaadinConnector self,
            String name, PropertyHandler propertyHandler)
            /*-{
                Object.defineProperty(self, name, {
                    enumerable: true,
                    get: $entry(function() { return propertyHandler.@PropertyHandler::get()(); }),
                    set: $entry(function(value) { propertyHandler.@PropertyHandler::set(*)(value); })
                });
            }-*/;

    private FakeParentConnector findFakeParent() {
        return (FakeParentConnector) getConnector().getConnection()
                .getConnector(parentConnectorId, -1);
    }

    private void updateFullState(ComponentConnector connector)
            throws NoDataException {
        AbstractComponentState state = connector.getState();
        Type stateType = getStateType(connector);
        JsArrayObject<Property> properties = stateType.getPropertiesAsArray();
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            JsonValue value;
            String name = property.getName();

            if (hasAttribute(propertyToAttribute(name))
                    || hasProperty(this, name)) {
                value = getAttributeOrProperty(name);
            } else {
                continue;
            }

            Object decodeValue = JsonDecoder.decodeValue(property.getType(),
                    value, null, connector.getConnection());
            property.setValue(state, decodeValue);
        }
    }

    private JsonValue getAttributeOrProperty(String propertyName) {
        String attributeName = propertyToAttribute(propertyName);
        if (hasAttribute(attributeName)) {
            String attributeValue = getAttribute(attributeName);
            try {
                return Json.parse(attributeValue);
            } catch (JsonException e) {
                return Json.create(attributeValue);
            }
        } else if (hasProperty(this, propertyName)) {
            return Util.jso2json(getPropertyJSO(propertyName));
        } else {
            return null;
        }
    }

    private static Type getStateType(ComponentConnector connector) {
        return new Type(connector.getState().getClass().getName(), null);
    }

    public final static native boolean hasProperty(VaadinConnector self,
            String name)
            /*-{
                return name in self;
            }-*/;

    private static native final ConnectorMap getConnectorMap(
            ApplicationConnection connection)
            /*-{
                return connection.@ApplicationConnection::connectorMap;
            }-*/;

    public final ComponentConnector getConnector() {
        return (ComponentConnector) getPropertyObject("connector");
    }

    public final void setConnector(ComponentConnector connector) {
        setPropertyObject("connector", connector);
    }

    public final void detachedCallback() {
        log("Detached");
        log(this);
    }

    public final void attributeChangedCallback(String attrName,
            JsonValue oldVal, JsonValue newVal) {
        log("Attribute change");
        log(this);
        log(attrName);
        log(oldVal);
        log(newVal);

        if (getConnector() == null) {
            // Connector not yet created
            return;
        }

        String propertyName = attributeToProperty(attrName);
        updateState(propertyName, newVal);
    }

    private static final String attributeToProperty(String attributeName) {
        StringBuilder b = new StringBuilder();
        String[] parts = attributeName.split("-");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i != 0) {
                part = Character.toUpperCase(part.charAt(0))
                        + part.substring(1);
            }
            b.append(part);
        }

        return b.toString();
    }

    private static final String propertyToAttribute(String propertyName) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < propertyName.length(); i++) {
            char c = propertyName.charAt(i);
            if (Character.isUpperCase(c)) {
                b.append('-');
                b.append(Character.toLowerCase(c));
            } else {
                b.append(c);
            }
        }

        return b.toString();
    }

    private void updateState(String propertyName, JsonValue value) {
        try {
            ComponentConnector connector = getConnector();
            Property property = getStateType(connector)
                    .getProperty(propertyName);
            Type propertyType = property.getType();
            if (propertyType == null) {
                log("Property not found in connector: " + propertyName);
                return;
            }

            Object decodedValue = JsonDecoder.decodeValue(propertyType, value,
                    null, connector.getConnection());
            property.setValue(connector.getState(), decodedValue);

            JsonObject stateChangeJson = Json.createObject();
            stateChangeJson.put(propertyName, value);

            log("Property " + propertyName + " changed to " + value.toJson());

            connector.fireEvent(
                    new StateChangeEvent(connector, stateChangeJson, false));
        } catch (NoDataException e) {
            log(propertyName);
            log(e);
        }
    }

    private JsonValue readState(String name) {
        try {
            ComponentConnector connector = getConnector();
            Property property = getStateType(connector).getProperty(name);
            Type propertyType = property.getType();

            Object value = property.getValue(connector.getState());

            return JsonEncoder.encode(value, propertyType,
                    connector.getConnection());

        } catch (NoDataException e) {
            log(name);
            log(e);
            return null;
        }
    }

    public static void log(Object message) {
        if (debug) {
            doLog(message);
        }
    }

    public static native void doLog(Object message)
    /*-{
        $wnd.console.log(message);
    }-*/;
}