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
package com.vaadin.hummingbird.html;

import com.vaadin.shared.ApplicationConstants;

/**
 * Component representing an <code>&lt;a&gt;</code> element with the router link
 * attribute. Router links are handled by the framework to perform view
 * navigation without a page reload.
 *
 * @since
 * @author Vaadin Ltd
 */
public class RouterLink extends Anchor {

    /**
     * Creates a new empty router link component.
     */
    public RouterLink() {
        setRouterLink();
    }

    /**
     * Creates an router link component with the given text content and href.
     *
     * @see #setHref(String)
     * @see #setText(String)
     *
     * @param href
     *            the href to set
     * @param text
     *            the text content to set
     */
    public RouterLink(String href, String text) {
        super(href, text);
        setRouterLink();
    }

    private void setRouterLink() {
        getElement().setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE,
                "");
    }
}
