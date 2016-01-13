package com.vaadin.hummingbird.voicechat;

import com.vaadin.annotations.Tag;
import com.vaadin.ui.AbstractComponent;

@Tag("div")
public class ChatLog extends AbstractComponent {

    public void addMessage(String message) {
        getElement().getNode().enqueueRpc("$0.innerText += $1;", getElement(),
                message + "\n");
    }
}
