package com.vaadin.hummingbird.voicechat;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Viewport("user-scalable=no,initial-scale=1.0")
@Push
public class VoiceChatUI extends UI {

    private VerticalLayout log;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        System.out.println(
                "Chatter from " + vaadinRequest.getRemoteAddr() + " joined");
        addComponent(new VoiceChat());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = VoiceChatUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
