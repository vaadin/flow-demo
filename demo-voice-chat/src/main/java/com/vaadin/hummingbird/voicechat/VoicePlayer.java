package com.vaadin.hummingbird.voicechat;

import java.util.Random;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.ui.AbstractComponent;

@HTML({ "vaadin://bower_components/voice-elements/dist/voice-player.html" })
@Tag("voice-player")
public class VoicePlayer extends AbstractComponent {

    public enum Accent {
        EN_US("en-US", "John"), EN_GB("en-GB", "Jeeves"), ES_ES("es-ES",
                "Pedro"), FR_FR("fr-FR", "Louis"), IT_IT("it-IT",
                        "Giuseppe"), DE_DE("de-DE", "Jürgen"), JA_JP("ja-JP",
                                "Aiko"), KO_KR("ko-KR",
                                        "Kyung-sook"), ZH_CN("zh-CN", "Chang");
        private String value;
        private String name;

        private Accent(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public static Accent from(String accentString) {
            for (Accent a : values()) {
                if (a.getValue().equalsIgnoreCase(accentString)) {
                    return a;
                }
            }
            return null;
        }

        public static Accent getRandom() {
            return Accent.values()[new Random(System.currentTimeMillis())
                    .nextInt(Accent.values().length)];
        }

        public String getName() {
            return name;
        }
    }

    public VoicePlayer() {
    }

    public void setAccent(Accent accent) {
        getElement().setAttribute("accent", accent.getValue());
    }

    public Accent getAccent() {
        String accentString = getElement().getAttribute("accent",
                Accent.EN_US.getValue());
        return Accent.from(accentString);
    }

    public void setText(String text) {
        getElement().setAttribute("text", text);
    }

    public String getText() {
        return getElement().getAttribute("text", "You are awesome");
    }

    public void speak() {
        getElement().invoke("speak");
    }
}
