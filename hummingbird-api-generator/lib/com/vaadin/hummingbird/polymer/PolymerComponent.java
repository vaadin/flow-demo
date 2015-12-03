package com.vaadin.hummingbird.polymer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.hummingbird.kernel.DomEventListener;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.ui.AbstractSimpleDOMComponentContainer;
import com.vaadin.ui.Component;

import elemental.json.JsonObject;

@SuppressWarnings("serial")
public abstract class PolymerComponent<T extends PolymerComponent<T>>
        extends AbstractSimpleDOMComponentContainer {

    public interface PolymerComponentClickListener extends DomEventListener {

        void handleClickEvent(MouseEventDetails mouseEventDetails);

        @Override
        default void handleEvent(JsonObject eventData) {
            handleClickEvent(new MouseEventDetails(eventData));
        }
    }

    protected abstract T getThis();

    public T setTextContent(String text) {
        getElement().setTextContent(text);
        return getThis();
    }

    public T setDisabled(boolean disabled) {
        setBooleanAttribute("disabled", disabled);
        return getThis();
    }

    public T setBooleanAttribute(String name, boolean value) {
        if (value) {
            getElement().setAttribute("attr." + name, "");
        } else {
            getElement().removeAttribute("attr." + name);
        }
        return getThis();
    }

    public boolean getBooleanAttribute(String value) {
        value = "attr." + value;
        return getElement().hasAttribute(value)
                && (getElement().getAttribute(value).isEmpty() || Boolean
                        .parseBoolean(getElement().getAttribute(value)));
    }

    public T setAttribute(String name, String value) {
        getElement().setAttribute("attr." + name, value);
        return getThis();
    }

    public T setNoink(boolean noink) {
        setBooleanAttribute("noink", noink);
        return getThis();
    }

    public T setTabindex(int index) {
        getElement().setAttribute("attr.tabindex", "" + index);
        return getThis();
    }

    public T setAriaLabel(String label) {
        getElement().setAttribute("attr.aria-label", label);
        return getThis();
    }

    public T withClassName(String className) {
        addStyleName(className);
        return getThis();
    }

    public T withClickListener(PolymerComponentClickListener listener) {
        if (!getElement().hasEventListeners("click")) {
            getElement().addEventData("click",
                    MouseEventDetails.getEventProperties());
        }
        getElement().addEventListener("click", listener);

        return getThis();
    }

    /**
     * @param attributes
     *            a list of attributes or attribute:values pairs. examples:
     *            setAttributes("foo bar") setAttributes("foo:bar ; hello:bye")
     */
    public T setAttributes(String attributes) {
        for (String attr : attributes.trim().replace(" *([;:]) *", "$1")
                .split("[; ]+")) {
            Matcher e = Pattern.compile(" *([\\w-]+)( *: *)?(.*)? *")
                    .matcher(attr);
            if (e.matches()) {
                if (e.group(3) != null) {
                    getElement().setAttribute("attr." + e.group(1), e.group(3));
                } else {
                    setBooleanAttribute(e.group(1), true);
                }
            }
        }
        return getThis();
    }

    public T with(Component... components) {
        addComponents(components);
        return getThis();
    }
}
