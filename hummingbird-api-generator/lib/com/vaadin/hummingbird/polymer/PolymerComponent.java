package com.vaadin.hummingbird.polymer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.ui.AbstractSimpleDOMComponentContainer;
import com.vaadin.ui.Component;

@SuppressWarnings("serial")
public abstract class PolymerComponent<T extends PolymerComponent<T>>
        extends AbstractSimpleDOMComponentContainer {

    protected abstract T getThis();

    public T setDisabled(boolean disabled) {
        setBooleanAttribute("disabled", disabled);
        return getThis();
    }

    public T setBooleanAttribute(String name, boolean value) {
        if (value) {
            getElement().setAttribute(name, "");
        } else {
            getElement().removeAttribute(name);
        }
        return getThis();
    }

    public boolean getBooleanAttribute(String value) {
        return getElement().hasAttribute(value)
                && (getElement().getAttribute(value).isEmpty() || Boolean
                        .parseBoolean(getElement().getAttribute(value)));
    }

    public T setNoink(boolean noink) {
        setBooleanAttribute("noink", noink);
        return getThis();
    }

    public T setTabindex(int index) {
        getElement().setAttribute("tabindex", "" + index);
        return getThis();
    }

    public T setAriaLabel(String label) {
        getElement().setAttribute("aria-label", label);
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
                    getElement().setAttribute(e.group(1), e.group(3));
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
