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
package com.vaadin.flow.demo.googlesignin.model;

import com.vaadin.flow.template.model.TemplateModel;

/**
 * Model interface for the user profile properties.
 */
public interface ProfileInfo extends TemplateModel {

    /**
     * Gets the user email from the model.
     * 
     * @return the email.
     */
    String getEmail();

    /**
     * Sets the user email in the model.
     * 
     * @param email
     *            the email.
     */
    void setEmail(String email);

    /**
     * Gets the user name from the model.
     * 
     * @return the user name.
     */
    String getName();

    /**
     * Sets the user name in the model.
     * 
     * @param name
     *            the user name.
     */
    void setName(String name);

    /**
     * Gets the user picture URL from the model.
     * 
     * @return the user picture URL.
     */
    String getPicture();

    /**
     * Sets the user picture URL in the model.
     * 
     * @param picture
     *            the user picture URL.
     */
    void setPicture(String picture);

}
