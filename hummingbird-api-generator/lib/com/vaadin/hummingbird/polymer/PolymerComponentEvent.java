package com.vaadin.hummingbird.polymer;

import com.vaadin.ui.Component.Event;

@SuppressWarnings("serial")
public abstract class PolymerComponentEvent extends Event {

    public PolymerComponentEvent(PolymerComponent<?> source) {
        super(source);
    }

    public PolymerComponent<?> getPolymerComponent() {
        return (PolymerComponent<?>) getSource();
    }

}
