package com.vaadin.hummingbird.addressbook;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.hummingbird.addressbook.backend.Contact;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class ContactForm extends FormLayout {

    Button save = new Button("Save", this::save);
    Button cancel = new Button("Cancel", this::cancel);
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField phone = new TextField("Phone");
    TextField email = new TextField("Email");
    DateField birthDate = new DateField("Birth date");

    Contact contact;

    BeanFieldGroup<Contact> formFieldBindings;

    public ContactForm() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        // save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        getElement().getNode().enqueueRpc(
                "$0.addEventListener('keyup', function(e) {e.keyCode == 13 && $1.click()})",
                getElement(), save.getElement());
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);

        addComponents(actions, firstName, lastName, phone, email, birthDate);
    }

    public void save(Button.ClickEvent event) {
        try {
            // Commit the fields from UI to DAO
            formFieldBindings.commit();

            // Save DAO to backend with direct synchronous service API
            getUI().service.save(contact);

            String msg = String.format("Saved '%s %s'.", contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg, Type.TRAY_NOTIFICATION);
            getUI().refreshContacts();
        } catch (FieldGroup.CommitException e) {
            // Validation exceptions could be shown here
        }
    }

    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
        getUI().contactList.select(null);
    }

    void edit(Contact contact) {
        this.contact = contact;
        if (contact != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact,
                    this);
            firstName.focus();
        }
        setVisible(contact != null);
    }

    @Override
    public AddressbookUI getUI() {
        return (AddressbookUI) super.getUI();
    }

}
