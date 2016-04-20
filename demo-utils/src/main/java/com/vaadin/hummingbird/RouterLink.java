/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird;

import com.vaadin.hummingbird.dom.ClassList;
import com.vaadin.hummingbird.dom.Style;
import com.vaadin.hummingbird.router.Router;
import com.vaadin.hummingbird.router.View;
import com.vaadin.shared.ApplicationConstants;

/**
 * A link that handles navigation internally using {@link Router} instead of
 * loading a new page in the browser.
 * <p>
 * The <code>href</code> attribute of {@link #getElement()} will only be
 * up-to-date when the component is attached to a UI.
 * <p>
 * TODO : this is temporary class which perhaps should not even be in a merged
 * PR (should be merged once #533 is fixed ?).
 *
 * @since
 * @author Vaadin Ltd
 */
public class RouterLink extends com.vaadin.hummingbird.router.RouterLink {

    /**
     * Creates a new empty router link.
     */
    public RouterLink() {
        getElement().setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE,
                "");
    }

    /**
     * Creates a new router link to the given view with the given text. The
     * provided parameters are used to populate placeholder and wildcard slots
     * in the route, starting from the beginning of the route. The number of
     * parameters must match the number of slots in the route.
     *
     * @param text
     *            the link text
     * @param viewType
     *            the view type to find a route for, not <code>null</code>
     * @param parameters
     *            the parameter values to set in the route
     */
    public RouterLink(String text, Class<? extends View> viewType,
            String... parameters) {
        this();
        setText(text);
        setRoute(viewType, parameters);
    }

    /**
     * Adds a CSS class name to this component.
     *
     * @param className
     *            the CSS class name to add, not <code>null</code>
     */
    public void addClassName(String className) {
        getClassList().add(className);
    }

    /**
     * Removes a CSS class name from this component.
     *
     * @param className
     *            the CSS class name to remove, not <code>null</code>
     * @return <code>true</code> if the class name was removed,
     *         <code>false</code> if the class list didn't contain the class
     *         name
     */
    public boolean removeClassName(String className) {
        return getClassList().remove(className);
    }

    /**
     * Sets the CSS class names of this component. This method overwrites any
     * previous set class names.
     *
     * @param className
     *            a space-separated string of class names to set, or
     *            <code>null</code> to remove all class names
     */
    public void setClassName(String className) {
        setAttribute("class", className);
    }

    /**
     * Gets the CSS class names used for this component.
     *
     * @return a space-separated string of class names, or <code>null</code> if
     *         there are no class names
     */
    public String getClassName() {
        return getAttribute("class");
    }

    /**
     * Sets or removes the given class name for this component.
     *
     * @param className
     *            the class name to set or remove, not <code>null</code>
     * @param set
     *            <code>true</code> to set the class name, <code>false</code> to
     *            remove it
     */
    public void setClassName(String className, boolean set) {
        getClassList().set(className, set);
    }

    private ClassList getClassList() {
        return getElement().getClassList();
    }

    /**
     * Gets the style instance for managing inline styles for the element of
     * this component.
     *
     * @return the style object for the element, not <code>null</code>
     */
    public Style getStyle() {
        return getElement().getStyle();
    }

    private void setAttribute(String name, String value) {
        assert name != null;
        if (value == null) {
            getElement().removeAttribute(name);
        } else {
            getElement().setAttribute(name, value);
        }
    }

    private String getAttribute(String name) {
        assert name != null;
        return getElement().getAttribute(name);
    }
}
