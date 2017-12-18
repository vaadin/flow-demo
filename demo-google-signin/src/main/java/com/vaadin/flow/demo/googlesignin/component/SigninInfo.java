/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.googlesignin.component;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.googlesignin.model.ProfileInfo;

/**
 * Component that shows the profile information of the logged user, such as
 * name, email and picture.
 */
@Tag("signin-info")
@HtmlImport("frontend://components/info.html")
public class SigninInfo extends PolymerTemplate<ProfileInfo>
        implements ProfileInfo {

    @Override
    public String getEmail() {
        return getModel().getEmail();
    }

    @Override
    public void setEmail(String email) {
        getModel().setEmail(email);
    }

    @Override
    public String getName() {
        return getModel().getName();
    }

    @Override
    public void setName(String name) {
        getModel().setName(name);
    }

    @Override
    public String getPicture() {
        return getModel().getPicture();
    }

    @Override
    public void setPicture(String picture) {
        getModel().setPicture(picture);
    }

}
