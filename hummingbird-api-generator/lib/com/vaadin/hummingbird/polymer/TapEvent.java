package com.vaadin.hummingbird.polymer;

import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;

import elemental.json.JsonObject;

@EventType("tap")
public class TapEvent<T extends PolymerComponent<T>>
        extends PolymerComponentEvent {

    @EventParameter("event.detail.x")
    private int clientX;
    @EventParameter("event.detail.y")
    private int clientY;

    // @EventParameter("event.detail.sourceEvent")
    private JsonObject sourceEvent;

    public TapEvent(PolymerComponent<?> source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getPolymerComponent() {
        return (T) super.getPolymerComponent();
    }

    public int getClientX() {
        return clientX;
    }

    public void setClientX(int clientX) {
        this.clientX = clientX;
    }

    public int getClientY() {
        return clientY;
    }

    public void setClientY(int clientY) {
        this.clientY = clientY;
    }

    public JsonObject getSourceEvent() {
        return sourceEvent;
    }

    public void setSourceEvent(JsonObject sourceEvent) {
        this.sourceEvent = sourceEvent;
    }

}
