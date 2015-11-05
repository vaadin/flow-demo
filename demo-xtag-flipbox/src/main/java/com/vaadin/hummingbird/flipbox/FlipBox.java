package com.vaadin.hummingbird.flipbox;

import java.util.EventObject;

import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.event.ElementEvents;
import com.vaadin.event.EventListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HTML;

@JavaScript({ "vaadin://bower_components/x-tag-core/dist/x-tag-core.min.js",
        "vaadin://bower_components/Flipbox/src/flipbox.js" })
@StyleSheet("vaadin://bower_components/Flipbox/src/flipbox.css")
@Tag("x-flipbox")
public class FlipBox extends AbstractComponent {

    public FlipBox() {
        setFront(new HTML(""));
        setBack(new HTML(""));
    }

    @Override
    protected void init() {
        super.init();
    }

    public void setFront(Component front) {
        assert front != null;
        getElement().setChild(0, front.getElement());
    }

    public void setBack(Component back) {
        assert back != null;
        getElement().setChild(1, back.getElement());
    }

    public void toggle() {
        getElement().invoke("toggle");
    }

    public void showFront() {
        getElement().invoke("showFront");
    }

    public void showBack() {
        getElement().invoke("showBack");
    }

    @EventType("flipend")
    public static class FlipEvent extends EventObject {

        @EventParameter("flipped")
        private boolean flipped;

        public FlipEvent(FlipBox flipBox) {
            super(flipBox);
        }

        public boolean isFlipped() {
            return flipped;
        }
    }

    public void addFlipListener(EventListener<FlipEvent> listener) {
        ElementEvents.addElementListener(this, FlipEvent.class, listener);
    }
}
