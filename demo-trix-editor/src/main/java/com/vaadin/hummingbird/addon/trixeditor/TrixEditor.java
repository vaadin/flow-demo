package com.vaadin.hummingbird.addon.trixeditor;

import java.util.EventObject;

import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.event.ElementEvents;
import com.vaadin.event.EventListener;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.AbstractField;

@JavaScript("vaadin://bower_components/trix/dist/trix.js")
@StyleSheet("vaadin://bower_components/trix/dist/trix.css")
@Tag("div")
public class TrixEditor extends AbstractField<String> {

    private Element editor;

    public TrixEditor() {
        // Wrapped in a <div> since the trix-editor automatically inserts a
        // <trix-toolbar> before <trix-editor>
        // This is not handled supported by the framework so any inserts after
        // this would use the wrong index
        editor = new Element("trix-editor");
        getElement().appendChild(editor);
    }

    @Override
    protected void init() {
        super.init();

    }

    @EventType("trix-change")
    public static class ValueChangeEvent extends EventObject {

        @EventParameter("element.value")
        private String value;

        public ValueChangeEvent(TrixEditor source) {
            super(source);
        }

        public String getValue() {
            return value;
        }
    }

    public void addValueListener(EventListener<ValueChangeEvent> listener) {
        ElementEvents.addElementListener(editor, this, ValueChangeEvent.class,
                listener);
    }

    @Override
    public Class<? extends String> getType() {
        return String.class;
    }

}
