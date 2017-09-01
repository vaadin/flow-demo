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
package com.vaadin.flow.demo.contactform.ui;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.Binder;
import com.vaadin.data.Binder.Binding;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.flow.html.Label;
import com.vaadin.flow.router.View;
import com.vaadin.server.SerializablePredicate;
import com.vaadin.ui.Button;
import com.vaadin.ui.Checkbox;
import com.vaadin.ui.Composite;
import com.vaadin.ui.DatePicker;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
@StyleSheet("frontend://src/style.css")
public class ContactForm extends Composite<FormLayout> implements View {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField phone = new TextField("Phone");
    private final TextField email = new TextField("Email");
    private final DatePicker birthDate = new DatePicker();
    private final Checkbox doNotCall = new Checkbox("Do not call");
    private final Button save = new Button("Save");
    private final Button reset = new Button("Reset");

    private final Binder<Contact> binder = new Binder<>();
    private final Contact contactBeingEdited = new Contact();

    private final Label infoLabel = new Label();

    /**
     * Creates an instance of contact form view.
     */
    public ContactForm() {
        UI.getCurrent().setLocale(Locale.ENGLISH);
        setId("contactform");

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.getClassNames().add("buttons");
        layout.add(save, reset);
        getContent().add(layout);

        birthDate.setLabel("Birth date");
        getContent().add(firstName, lastName, phone, email, birthDate,
                doNotCall);

        infoLabel.setId("info");

        getContent().add(infoLabel);

        firstName.setId("first-name");
        lastName.setId("last-name");
        phone.setId("phone");
        email.setId("email");
        birthDate.setId("birth-date");
        doNotCall.setId("do-not-call");

        save.setId("save");
        reset.setId("reset");
        configureComponents();
    }

    @Override
    protected FormLayout initContent() {
        return new FormLayout();
    }

    private void configureComponents() {
        final SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
                .getValue().trim().isEmpty()
                || !email.getValue().trim().isEmpty();

        Binding<Contact, String> emailBinding = binder.forField(email)
                .withValidator(phoneOrEmailPredicate,
                        "Both phone and email cannot be empty")
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(Contact::getEmail, Contact::setEmail);

        Binding<Contact, String> phoneBinding = binder.forField(phone)
                .withValidator(phoneOrEmailPredicate,
                        "Both phone and email cannot be empty")
                .bind(Contact::getPhone, Contact::setPhone);

        // Trigger cross-field validation when the other field is changed
        email.addValueChangeListener(event -> phoneBinding.validate());
        phone.addValueChangeListener(event -> emailBinding.validate());

        firstName.setRequiredIndicatorVisible(true);
        lastName.setRequiredIndicatorVisible(true);

        binder.bind(firstName, Contact::getFirstName, Contact::setFirstName);
        binder.bind(lastName, Contact::getLastName, Contact::setLastName);
        binder.bind(doNotCall, Contact::isDoNotCall, Contact::setDoNotCall);
        binder.bind(birthDate, Contact::getBirthDate, Contact::setBirthDate);

        save.addClickListener(this::save);
        reset.addClickListener(this::reset);
    }

    private void save(Button.ClickEvent<Button> event) {
        if (binder.writeBeanIfValid(contactBeingEdited)) {
            infoLabel.setText("Saved bean values :" + contactBeingEdited);
        } else {
            BinderValidationStatus<Contact> validate = binder.validate();
            infoLabel.setText(
                    "There are errors :" + getValidationErrors(
                            validate.getFieldValidationStatuses()));
        }
    }

    private String getValidationErrors(
            List<BindingValidationStatus<?>> statuses) {
        return statuses.stream().filter(BindingValidationStatus::isError)
                .map(BindingValidationStatus::getMessage).map(Optional::get)
                .distinct()
                .collect(Collectors.joining(", "));
    }

    private void reset(Button.ClickEvent<Button> event) {
        // clear fields via setting <code>null</code>
        binder.readBean(null);
        infoLabel.setText("");
        doNotCall.setValue(false);
    }
}
