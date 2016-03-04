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
package com.vaadin.hummingbird.demo.website;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.HasSubView;
import com.vaadin.hummingbird.router.View;

/**
 * Special layout for the about page.
 *
 * @since
 * @author Vaadin Ltd
 */
public class AboutLayout extends SimpleView implements HasSubView {

    /**
     * Creates a about page.
     */
    public AboutLayout() {
        super(new Element("div").appendChild(new Element("p")
                .setTextContent("Special layout for the about page.")));
    }

    @Override
    public void setSubView(View content) {
        Element contentElement = content.getElement();
        Element element = getElement();

        if (element.getChildCount() == 2) {
            Element lastChild = element.getChild(1);
            if (lastChild.equals(contentElement)) {
                return;
            }

            lastChild.removeFromParent();
        }

        element.appendChild(contentElement);
    }

}
