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
package com.vaadin.flow.demo.helloworld.template;

import java.util.Date;
import java.util.logging.Logger;

import com.vaadin.annotations.Convert;
import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.demo.helloworld.template.SignInForm.SignInModel;
import com.vaadin.flow.router.View;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * The one and only view in the SignIn application.
 */
@Tag("sign-in-form")
@HtmlImport("frontend://components/SignInForm.html")
public class SignInForm extends PolymerTemplate<SignInModel> implements View {

    public interface SignInModel extends TemplateModel {

        /**
         * Gets the name of a registrant.
         *
         * @return
         */
        String getName();

        /**
         * Sets the name of a registrant.
         *
         * @param the
         *            name of a registrant
         */
        void setName(String name);

        /**
         * Social security number. It has {@link Long} type and is supposed to
         * be stored as a {@link Long} in a database.
         * <p>
         * Flow doesn't support {@link Long} since this type doesn't exist on
         * the client side. So it should be converted to some supported type.
         *
         * @param ssd
         *            the social security number of a registrant
         */
        @Convert(LongToStringConverter.class)
        void setSsd(Long ssd);

        /**
         * Gets the name of a registrant.
         *
         * @param the
         *            social sceurity number of a registrant
         */
        Long getSsd();

        /**
         * Returns the date of birth.
         * <p>
         * One {@link Date} value will be used in 3 different input fields: a
         * separate day field, a month field and an year field. So converter is
         * used here to convert one value into a bean with 3 properties.
         *
         * @param date
         *            the date of birth
         */
        @Convert(DateToDateBeanConverter.class)
        void setBirthDate(Date date);

        /**
         * Gets the the date of birth
         *
         * @return the date of birth
         */
        Date getBirthDate();

        void setRegistrationMessage(String msg);
    }

    public SignInForm() {
        setId("template");
        getModel().setBirthDate(new Date());
    }

    @EventHandler
    private void signIn() {
        String name = getModel().getName();
        Long ssd = getModel().getSsd();
        Date date = getModel().getBirthDate();
        Logger.getLogger(SignInForm.class.getName())
                .info("Register a new user with the name '" + name
                        + "; and  SSS  '" + ssd + "', and birthday: " + date);
        Date current = new Date();
        getModel().setRegistrationMessage("Welcome " + name + ", your are "
                + (current.getYear() - date.getYear()) + " old");
    }
}
