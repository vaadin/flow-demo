package com.vaadin.hummingbird.vaadinconnector.client;

import java.util.Collections;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorHierarchyChangeEvent;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.ui.AbstractSingleComponentContainerConnector;
import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.communication.SharedState;

public class FakeParentConnector
        extends AbstractSingleComponentContainerConnector {

    private static class FakeParentWidget extends SimplePanel {
        private FakeParentWidget(Element elem) {
            super(elem);
        }

        @Override
        public void onAttach() {
            // Just expose to enclosing class
            super.onAttach();
        }

        @Override
        public void onDetach() {
            // Just expose to enclosing class
            super.onDetach();
        }
    }

    private FakeParentWidget widget;

    public FakeParentConnector(Element element) {
        widget = new FakeParentWidget(element);

        // Mark as attached
        widget.onAttach();
    }

    @Override
    public void updateCaption(ComponentConnector connector) {
        // nop
    }

    @Override
    public Widget getWidget() {
        return widget;
    }

    @Override
    protected SharedState createState() {
        return new AbstractComponentState();
    }

    public void setChild(ComponentConnector child) {
        List<ComponentConnector> childComponents = getChildComponents();

        if (childComponents.size() == 1) {
            ComponentConnector oldChild = childComponents.get(0);
            widget.remove(oldChild.getWidget());
            oldChild.setParent(null);
        }

        if (child != null) {
            setChildren(Collections.singletonList((ServerConnector) child));
            child.setParent(this);

            widget.setWidget(child.getWidget());
        } else {
            setChildren(Collections.<ServerConnector> emptyList());
        }
    }

    @Override
    public void onConnectorHierarchyChange(
            ConnectorHierarchyChangeEvent connectorHierarchyChangeEvent) {
        // nop
    }

}
