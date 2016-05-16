package com.vaadin.hummingbird.demo.webcomponent.progressbubble;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.ui.Component;

@Tag("progress-bubble")
@HtmlImport(PolyGit.BASE_URL + "progress-bubble/progress-bubble.html")
public class ProgressBubble extends Component {

    /**
     * Creates a new progress bubble with the default value (0) and max value
     * (100).
     */
    public ProgressBubble() {
    }

    /**
     * Creates a new progress bubble with the given value and max value.
     *
     * @param value
     *            the initial value to use
     * @param max
     *            the max value to use
     */
    public ProgressBubble(int value, int max) {
        setValue(value);
        setMax(max);
    }

    /**
     * Sets the value.
     *
     * @param value
     *            the value to set, must be between 0 and {@link #getMax()}.
     */
    public void setValue(int value) {
        if (value < 0) {
            value = 0;
        }
        if (value > getMax()) {
            value = getMax();
        }
        getElement().setProperty("value", value + "");
        getElement().setTextContent(value + " %");
    }

    /**
     * Gets the current value.
     *
     * @return the current value
     */
    public int getValue() {
        return getElement().getProperty("value", 0);
    }

    /**
     * Sets the max value.
     * <p>
     * The max value corresponds to 100% progress.
     *
     * @param max
     *            the max value to use
     */
    public void setMax(int max) {
        getElement().setProperty("max", max);
    }

    /**
     * Gets the max value.
     * <p>
     * The max value corresponds to 100% progress.
     *
     * @return the max value
     */
    public int getMax() {
        return getElement().getProperty("max", 100);
    }

}