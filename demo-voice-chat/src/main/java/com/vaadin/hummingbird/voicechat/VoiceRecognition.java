package com.vaadin.hummingbird.voicechat;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.ui.AbstractComponent;

@HTML({ "vaadin://bower_components/voice-elements/dist/voice-recognition.html" })
@Tag("voice-recognition")
public class VoiceRecognition extends AbstractComponent {

    public VoiceRecognition() {
        setWidth("300px");
        setHeight("300px");
    }

}
