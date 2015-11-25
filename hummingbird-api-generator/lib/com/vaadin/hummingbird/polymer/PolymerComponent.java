package com.vaadin.hummingbird.polymer;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import com.vaadin.ui.AbstractSimpleDOMComponentContainer;

@SuppressWarnings("serial")
public abstract class PolymerComponent extends AbstractSimpleDOMComponentContainer {

    public PolymerComponent setDisabled(boolean disabled) {
        setBooleanAttribute("disabled", disabled);
        return this;
    }

    public void setBooleanAttribute(String name, boolean value) {
        if (value) {
            getElement().setAttribute(name, "");
        } else {
            getElement().removeAttribute(name);
        }
    }

    public boolean getBooleanAttribute(String value) {
        return getElement().hasAttribute(value)
                && (getElement().getAttribute(value).isEmpty()
                    || Boolean.parseBoolean(getElement().getAttribute(value)));
    }

    public PolymerComponent setNoink(boolean noink) {
        setBooleanAttribute("noink", noink);
        return this;
    }
    public PolymerComponent setTabindex(int index) {
        getElement().setAttribute("tabindex", "" + index);
        return this;
    }

    public PolymerComponent setAriaLabel(String label) {
        getElement().setAttribute("aria-label", label);
        return this;
    }

    /**
     * @param attributes a list of attributes or attribute:values pairs.
     *        examples:
     *          setAttributes("foo bar")
     *          setAttributes("foo:bar ; hello:bye")
     */
    public void setAttributes(String attributes) {
        for (String attr : attributes.trim().replace(" *([;:]) *", "$1")
             .split("[; ]+")) {
            MatchResult e = Pattern.compile(" *([\\w-]+)( *: *)?(.*)? *")
                .matcher(attr);
            
            if (e.group(3) != null) {
                getElement().setAttribute(e.group(1), e.group(3));
            } else {
                setBooleanAttribute(e.group(1), true);
            }
        }
    }
}
