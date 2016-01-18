package com.vaadin.hummingbird.complexform.ui;

import java.util.List;

import com.vaadin.hummingbird.complexform.model.Backend;
import com.vaadin.hummingbird.complexform.model.entity.Goal;
import com.vaadin.hummingbird.complexform.model.entity.Kpi;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperHeaderPanel;
import com.vaadin.hummingbird.paper.PaperStyles;
import com.vaadin.hummingbird.paper.PaperToolbar;
import com.vaadin.ui.CssLayout;

@com.vaadin.annotations.HTML({
        "context://bower_components/paper-styles/demo-pages.html",
        "context://bower_components/promise-polyfill/promise-polyfill-lite.html" })
public class MainView extends PaperHeaderPanel {

    private CssLayout content;

    public MainView() {
        content = new CssLayout();
        content.addStyleName("fit");

        with(new PaperStyles(), new Menu(), content).withClassName("flex");

        onAdd();
    }

    private void onAdd() {
        List<Goal> goalOptions = Backend.getGoalOptions();
        Kpi newKpi = Backend.createNewKpi();

        KpiForm kpiForm = new KpiForm();
        content.removeAllComponents();
        content.addComponent(kpiForm);

        kpiForm.setGoalOptions(goalOptions);
        kpiForm.setKpi(newKpi);

    }

    class Menu extends PaperToolbar {

        public Menu() {
            CssLayout wrapper = new CssLayout();
            wrapper.setWidth("100%");
            wrapper.addStyleName("horizontal center-justified layout");
            wrapper.addComponent(new PaperButton().setTextContent("Add")
                    .setRaised(true).withClickListener(e -> onAdd()));

            addComponent(wrapper);
        }
    }

}
