package com.vaadin.hummingbird.voicechat;

import com.vaadin.hummingbird.voicechat.Broadcaster.MessageListener;
import com.vaadin.hummingbird.voicechat.VoicePlayer.Accent;
import com.vaadin.ui.Template;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

public class VoiceChat extends Template {

    public interface Model extends Template.Model {
        public boolean isVoiceEnabled();

        public void setVoiceEnabled(boolean voiceEnabled);
    }

    private VoiceRecognition recognition;
    private VoicePlayer player;
    private TextField messageInput;
    private ChatLog chatLog;

    private Accent myAccent = Accent.EN_US;

    @Override
    protected Model getModel() {
        return (Model) super.getModel();
    }

    public VoiceChat() {
    }

    MessageListener listener = e -> {
        onMessage(e.getAccent(), e.getMessage(), e.getSource());
    };

    @Override
    protected void init() {
        Broadcaster.addMessageListener(listener);
        messageInput.addValueChangeListener(e -> {
            String msg = ((String) e.getProperty().getValue()).trim();
            if (!msg.isEmpty()) {
                Broadcaster.sendMessage(msg, myAccent, getUI());
            }
            messageInput.setValue("");
        });
        myAccent = Accent.getRandom();
        Broadcaster.sendMessage("Hello, I am here now!", myAccent, getUI());
        recognition.addResultListener(e -> {
            Broadcaster.sendMessage(e.getText(), myAccent, getUI());
        });
    }

    private void onMessage(Accent accent, String msg, UI source) {
        try {
            getUI().access(() -> {
                chatLog.addMessage(accent.getName() + ": " + msg);
                player.setAccent(accent);
                player.setText(msg);
                player.speak();

            });
        } catch (Exception e) {
            Broadcaster.removeMessageListener(listener);
        }
    }
}
