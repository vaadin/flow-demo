package com.vaadin.hummingbird.vaadinconnector.client;

import com.google.gwt.core.client.EntryPoint;
import com.vaadin.client.metadata.ConnectorBundleLoader;

import elemental.json.JsonValue;

public class VaadinConnectorExporter implements EntryPoint {

    @SuppressWarnings("unused")
    // GWT didn't allow calling JSO methods straight from JSNI, so using these
    // bridge methods
    private static class JsniBridge {
        public static void createdCallback(VaadinConnector connector) {
            connector.createdCallback();
        }

        public static void attachedCallback(VaadinConnector connector) {
            connector.attachedCallback();
        }

        public static void detachedCallback(VaadinConnector connector) {
            connector.detachedCallback();
        }

        public static void attributeChangedCallback(VaadinConnector connector,
                String attrName, JsonValue oldVal, JsonValue newVal) {
            connector.attributeChangedCallback(attrName, oldVal, newVal);
        }
    }

    @Override
    public void onModuleLoad() {
        // Only support eager connectors for now
        ConnectorBundleLoader.get()
                .loadBundle(ConnectorBundleLoader.EAGER_BUNDLE_NAME, null);

        registerWebComponent();
    }

    private static native void registerWebComponent()
    /*-{
        var proto = Object.create(HTMLElement.prototype);

        proto.createdCallback = $entry(function() {@JsniBridge::createdCallback(*)(this)});

        proto.attachedCallback = $entry(function() {@JsniBridge::attachedCallback(*)(this)});

        proto.detachedCallback = $entry(function() {@JsniBridge::detachedCallback(*)(this)});

        proto.attributeChangedCallback = $entry(function(attrName, oldVal, newVal) {@JsniBridge::attributeChangedCallback(*)(this, attrName, oldVal, newVal)});

        $doc.registerElement('vaadin-connector', {
            prototype: proto,
        });
    }-*/;

}
