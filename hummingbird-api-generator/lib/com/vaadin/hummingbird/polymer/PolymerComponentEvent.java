package com.vaadin.hummingbird.polymer;

import java.util.Optional;

import com.vaadin.hummingbird.kernel.JsonConverter;
import com.vaadin.ui.Component.Event;

import elemental.json.JsonObject;

@SuppressWarnings("serial")
public abstract class PolymerComponentEvent extends Event {

    private JsonObject eventData;

    public PolymerComponentEvent(PolymerComponent<?> source) {
        super(source);
    }

    public PolymerComponent<?> getPolymerComponent() {
        return (PolymerComponent<?>) getSource();
    }

    public JsonObject getEventData() {
        return eventData;
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T getEventData(Class<T> type, String key) {
        if (eventData != null && eventData.hasKey(key)) {
            return (T) JsonConverter.fromJson(type, eventData.get(key));
        } else {
            return null;
        }
    }

    public Optional<Boolean> getEventDataBoolean(String key) {
        return Optional.ofNullable(getEventData(Boolean.class, key));
    }

    public Optional<String> getEventDataString(String key) {
        return Optional.ofNullable(getEventData(String.class, key));
    }

    public Optional<Integer> getEventDataInteger(String key) {
        return Optional.ofNullable(getEventData(Integer.class, key));
    }

    public Optional<Float> getEventDataFloat(String key) {
        return Optional.ofNullable(getEventData(Float.class, key));
    }
}
