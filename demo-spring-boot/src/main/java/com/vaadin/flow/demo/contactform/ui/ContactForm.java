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

import org.vaadin.textfieldformatter.CustomStringBlockFormatter;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
@Route("")
@Theme(Lumo.class)
@JsModule("./styles.js")
public class ContactForm extends Composite<VerticalLayout> {

    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("Last name");
    private final TextField phone = new TextField("Phone");
    private final EmailField email = new EmailField("Email");
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

        FormLayout formLayout = new FormLayout();

        birthDate.setLabel("Birth date");
        formLayout.add(firstName, lastName, phone, email, birthDate, doNotCall);

        infoLabel.setId("info");

        getContent().add(infoLabel);

        firstName.setId("first-name");
        lastName.setId("last-name");
        phone.setId("phone");
        email.setId("email");
        birthDate.setId("birth-date");
        doNotCall.setId("do-not-call");

        CustomStringBlockFormatter formatter = new CustomStringBlockFormatter.Builder()
                .blocks(3, 3, 2, 2).delimiters("-").prefix("+1", " ").numeric()
                .build();
        formatter.extend(phone);

        save.setId("save");
        reset.setId("reset");
        configureComponents();

        getContent().add(formLayout);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.getClassNames().add("buttons");
        layout.add(save, reset);
        getContent().add(layout);
    }

    @Override
    protected VerticalLayout initContent() {
        return new VerticalLayout();
    }

    private void configureComponents() {
        final SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
                .getValue().trim().isEmpty()
                || !email.getValue().trim().isEmpty();

        Binder.Binding<Contact, String> emailBinding = binder.forField(email)
                .withValidator(phoneOrEmailPredicate,
                        "Both phone and email cannot be empty")
                .withValidator(new EmailValidator("Incorrect email address"))
                .bind(Contact::getEmail, Contact::setEmail);

        Binder.Binding<Contact, String> phoneBinding = binder.forField(phone)
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

    private void save(ClickEvent<Button> event) {
        if (binder.writeBeanIfValid(contactBeingEdited)) {
            infoLabel.setText("Saved bean values :" + contactBeingEdited);
        } else {
            BinderValidationStatus<Contact> validate = binder.validate();
            infoLabel.setText("There are errors :" + getValidationErrors(
                    validate.getFieldValidationStatuses()));
        }
    }

    private String getValidationErrors(
            List<BindingValidationStatus<?>> statuses) {
        return statuses.stream().filter(BindingValidationStatus::isError)
                .map(BindingValidationStatus::getMessage).map(Optional::get)
                .distinct().collect(Collectors.joining(", "));
    }

    private void reset(ClickEvent<Button> event) {
        // clear fields via setting <code>null</code>
        binder.readBean(null);
        infoLabel.setText("");
        doNotCall.setValue(false);
    }
}
