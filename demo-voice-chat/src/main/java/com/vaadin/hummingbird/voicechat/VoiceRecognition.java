package com.vaadin.hummingbird.voicechat;

import java.util.EventObject;

import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.event.ElementEvents;
import com.vaadin.event.EventListener;
import com.vaadin.ui.AbstractComponent;

@HTML({ "vaadin://bower_components/voice-elements/dist/voice-recognition.html" })
@Tag("voice-recognition")
public class VoiceRecognition extends AbstractComponent {

    public VoiceRecognition() {
        setWidth("300px");
        setHeight("300px");
    }

    @EventType("result")
    public static class ResultEvent extends EventObject {

        @EventParameter("event.detail.results[event.detail.resultIndex][0].transcript")
        private String text;

        public ResultEvent(VoiceRecognition source) {
            super(source);
        }

        public String getText() {
            return text;
        }
    }

    public void addResultListener(EventListener<ResultEvent> listener) {
        ElementEvents.addElementListener(this, ResultEvent.class, listener);

    }

}
