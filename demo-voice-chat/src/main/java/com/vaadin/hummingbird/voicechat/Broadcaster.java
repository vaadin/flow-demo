package com.vaadin.hummingbird.voicechat;

import java.util.EventListener;
import java.util.EventObject;

import com.vaadin.event.EventRouter;
import com.vaadin.hummingbird.voicechat.Broadcaster.MessageListener.MessageEvent;
import com.vaadin.hummingbird.voicechat.VoicePlayer.Accent;
import com.vaadin.ui.UI;

public class Broadcaster {

    private static Broadcaster instance = new Broadcaster();

    private EventRouter router = new EventRouter();

    public interface MessageListener extends EventListener {

        public static class MessageEvent extends EventObject {
            String message;
            private Accent accent;

            public MessageEvent(Accent accent, String message, UI source) {
                super(source);
                this.message = message;
                this.accent = accent;
            }

            @Override
            public UI getSource() {
                return (UI) super.getSource();
            }

            public String getMessage() {
                return message;
            }

            public Accent getAccent() {
                return accent;
            }

        }

        public void messageReceived(MessageEvent messageEvent);
    }

    public static void sendMessage(String message, Accent accent, UI source) {
        instance.router.fireEvent(new MessageEvent(accent, message, source));
    }

    public static void addMessageListener(MessageListener messageListener) {
        instance.router.addListener(MessageEvent.class, messageListener);
    }

    public static void removeMessageListener(MessageListener messageListener) {
        instance.router.removeListener(MessageEvent.class, messageListener);
    }

}