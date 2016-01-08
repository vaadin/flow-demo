package com.vaadin.hummingbird.demo.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.vaadin.event.EventListener;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.iron.event.IronSelectEvent;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperDropdownMenu;
import com.vaadin.hummingbird.paper.PaperInput;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperMenu;
import com.vaadin.hummingbird.paper.PaperRadioButton;
import com.vaadin.hummingbird.paper.PaperRadioGroup;
import com.vaadin.hummingbird.paper.event.ChangeEvent;
import com.vaadin.ui.CssLayout;

import elemental.json.JsonType;
import elemental.json.JsonValue;

public class CustomerFormDemo extends SampleBase {

    private final EventListener<ChangeEvent<PaperInput>> basicInputChangeListener = e -> onInputChange(
            e);
    private static final String[] statuses = new String[] { "Imported Lead",
            "Not Contacted", "Contacted", "Customer", "Closed Lost" };

    private Map<String, Object> values = new HashMap<>();
    private Map<String, Object> cached = new HashMap<>();

    private PaperDialog eventDialog;

    private CssLayout root;

    public CustomerFormDemo() {
        addStyleName("customer-form-sample");
        root = root();
        addComponent(root);

        addBasicVersion(root);

        eventDialog = new PaperDialog().addIronOverlayClosedListener(
                e -> eventDialog.setOpened(false));
        root.addComponent(eventDialog);
    }

    private void addBasicVersion(CssLayout root) {
        CssLayout section = createHorizontalSection(root,
                "Form with manual listeners");

        section.addComponents(
                // inputs
                new PaperInput().setValue((String) getValue("firstName"))
                        .setLabel("First name").setName("firstName")
                        .addEventData(ChangeEvent.class, "event.target.value")
                        .addChangeListener(basicInputChangeListener),
                new PaperInput().setValue((String) getValue("lastName"))
                        .setLabel("Last name").setName("lastName")
                        .addEventData(ChangeEvent.class, "event.target.value")
                        .addChangeListener(basicInputChangeListener),
                new PaperInput().setValue((String) getValue("email"))
                        .setLabel("Email").setName("email")
                        .addEventData(ChangeEvent.class, "event.target.value")
                        .addChangeListener(
                                basicInputChangeListener),
                new PaperInput().setValue((String) getValue("birthDate"))
                        .setLabel("Birthday").setName("birthDate")
                        .setType("date")
                        .addEventData(ChangeEvent.class, "event.target.value")
                        .addChangeListener(basicInputChangeListener),
                // radio buttons
                layout("flex",
                        new PaperRadioGroup().setAttrForSelected("name")
                                .setSelected((String) getValue("gender"))
                                .addEventData("iron-select",
                                        "event.target.selected")
                        .addIronSelectListener(e -> {
                            cached.put("gender",
                                    e.getEventDataString(
                                            "event.target.selected")
                                    .orElse(null));
                        }).with(new PaperRadioButton().setName("Female")
                                .setTextContent("Female"),
                                new PaperRadioButton().setName("Male")
                                        .setTextContent("Male"))),
                // drop down
                new PaperDropdownMenu().setLabel("Status").with(new PaperMenu()
                        .withClassName("dropdown-content")
                        // .setAttrForSelected("value") // doesn't work
                        .addEventData("iron-select", "event.target.selected")
                        .setSelected(getValue("status") == null ? "-1"
                                : getValue("status").toString())
                        .addIronSelectListener(e -> onStatusSelect(e))
                        .with(Stream.of(statuses).map(str -> {
                            return new PaperItem()
                                    .setAttribute("value", str.replace(" ", ""))
                                    .setTextContent(str);
                        }).toArray(PaperItem[]::new))));

        CssLayout buttons = layout("flex");
        buttons.addComponents(
                new PaperButton().setTextContent("Save").setRaised(true)
                        .withClickListener(e -> onSave()),
                new PaperButton().setTextContent("Reset").setRaised(true)
                        .withClickListener(e -> onReset()));
        section.addComponents(buttons);
    }

    private Object getValue(String key) {
        return values.get(key);
    }

    private void onSave() {
        values.putAll(cached);
        eventDialog.removeAllComponents();

        CssLayout section = createHorizontalSection(eventDialog,
                "Saved values:");
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String value = entry.getValue() != null
                    ? entry.getValue().toString() : "<empty>";

            section.addComponents(layout("layout horizontal wrap",
                    element("div", entry.getKey(), false),
                    element("div", " : ", false), element("div", value, false),
                    br()));
        }
        eventDialog.open();
    }

    private void onReset() {
        cached.clear();
        // recreate everything
        root.removeAllComponents();
        root.addComponents(eventDialog);
        addBasicVersion(root);
    }

    private void onInputChange(ChangeEvent<PaperInput> e) {
        cached.put(e.getPolymerComponent().getName(),
                e.getEventDataString("event.target.value").orElse(null));
    }

    private void onStatusSelect(IronSelectEvent<PaperMenu> selectEvent) {
        JsonValue jsonValue = selectEvent.getEventData()
                .get("event.target.selected");
        if (jsonValue != null) {
            if (jsonValue.getType() == JsonType.STRING) {
                cached.put("status",
                        selectEvent.getEventDataString("event.target.selected")
                                .orElse("-1"));
            } else if (jsonValue.getType() == JsonType.NUMBER) {
                cached.put("status",
                        selectEvent.getEventDataInteger("event.target.selected")
                                .orElse(-1).toString());
            }
        }
    }

}