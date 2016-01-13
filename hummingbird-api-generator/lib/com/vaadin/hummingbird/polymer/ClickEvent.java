package com.vaadin.hummingbird.polymer;

import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;

@EventType("click")
public class ClickEvent<T extends PolymerComponent<T>>
        extends PolymerComponentEvent {

    @EventParameter
    private boolean ctrlKey;
    @EventParameter
    private boolean shiftKey;
    @EventParameter
    private boolean altKey;
    @EventParameter
    private boolean metaKey;

    @EventParameter
    private int clientX;
    @EventParameter
    private int clientY;

    @EventParameter
    private int screenX;
    @EventParameter
    private int screenY;

    @EventParameter
    private int button;

    @EventParameter
    private String type;

    public ClickEvent(PolymerComponent<?> source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getPolymerComponent() {
        return (T) super.getPolymerComponent();
    }

    public boolean isCtrlKey() {
        return ctrlKey;
    }

    public void setCtrlKey(boolean ctrlKey) {
        this.ctrlKey = ctrlKey;
    }

    public boolean isShiftKey() {
        return shiftKey;
    }

    public void setShiftKey(boolean shiftKey) {
        this.shiftKey = shiftKey;
    }

    public boolean isAltKey() {
        return altKey;
    }

    public void setAltKey(boolean altKey) {
        this.altKey = altKey;
    }

    public boolean isMetaKey() {
        return metaKey;
    }

    public void setMetaKey(boolean metaKey) {
        this.metaKey = metaKey;
    }

    public int getClientX() {
        return clientX;
    }

    public void setClientX(int clientX) {
        this.clientX = clientX;
    }

    public int getClientY() {
        return clientY;
    }

    public void setClientY(int clientY) {
        this.clientY = clientY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
