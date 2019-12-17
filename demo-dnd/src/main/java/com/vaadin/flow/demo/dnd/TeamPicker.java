/*
 * Copyright 2000-2019 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.flow.demo.dnd;

import java.util.Random;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DragStartEvent;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropEvent;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.dnd.EffectAllowed;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@JsModule("@vaadin/vaadin-lumo-styles/all-imports")
@RouteAlias("dnd-demo")
@Route(value = "team-picker", layout = MainLayout.class)
public class TeamPicker extends Div {

    private static final String[] FIRST_NAMES = { "Artur", "Antti", "Anssi",
            "Eero", "Erik", "Hannu", "Henri", "Johannes", "Leif", "Jarmo",
            "Jens", "Joacim", "Joonas", "Jouni", "Juha", "Juho", "Jurka",
            "Juuso", "Kari", "Kim", "Lauri", "Marc", "Marcus", "Markus",
            "Matti", "Mikael", "Mikko", "Olli", "Pekka", "Peter", "Petter",
            "Tomi", "Teppo", "Tomi" };
    private static final String[] LAST_NAMES = { "Duarte", "Al Kaleedy",
            "Smirnova", "Harvey", "Shrestha", "Emelyanova", "Platonov", "Godin",
            "Dudeja", "Onubeze", "Wilson", "Bui", "Udrescu", "Saxby", "Binzet",
            "Cardoso", "Pramadham", "Haase", "Koch", "Torrezan Filho",
            "Alvarez", "Alkali", "Rucidlo", "Treml", "Gutierrez", "Carrasco",
            "Vyšný", "Lottmann", "Rauturier", "Javan" };

    private static final String SLOT_HEIGHT = "20px";
    private static final String SLOT_WIDTH = "100px";

    private final NumberField runnersField;
    private final VerticalLayout allRunnersLayout;
    private final NumberField teamsField;
    private final HorizontalLayout teamsLayout;

    public TeamPicker() {
        teamsField = new NumberField();
        teamsField.setMin(1);
        teamsField.setMax(5);
        teamsField.setValue(3D);
        teamsField.setLabel("Teams");

        runnersField = new NumberField();
        runnersField.setValue(7D);
        runnersField.setMin(2);
        runnersField.setMax(25);
        runnersField.setLabel("Runners per team");

        Button goButton = new Button("Go", event -> reset());
        goButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        allRunnersLayout = new VerticalLayout();
        allRunnersLayout.setWidth("200px");
        allRunnersLayout.getStyle().set("border", "1px solid black");

        DropTarget<VerticalLayout> dropTarget = DropTarget
                .configure(allRunnersLayout);
        dropTarget.setDropEffect(DropEffect.MOVE);
        dropTarget.addDropListener(this::onRunnerDropBackToList);

        teamsLayout = new HorizontalLayout();
        teamsLayout.setMargin(true);

        HorizontalLayout options = new HorizontalLayout(teamsField,
                runnersField, goButton);
        options.setMargin(true);
        options.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.BASELINE);
        add(options, teamsLayout);

        reset();
    }

    private void reset() {
        teamsLayout.removeAll();
        resetRunners();
        resetTeams();
    }

    private void resetRunners() {
        allRunnersLayout.removeAll();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            String name = new StringBuilder(
                    FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]).append(" ")
                            .append(LAST_NAMES[random
                                    .nextInt(LAST_NAMES.length)])
                            .toString();
            allRunnersLayout.add(createRunner(name));
        }
        teamsLayout.add(allRunnersLayout);
    }

    private Component createRunner(String name) {
        Div runner = new Div();
        runner.setText(name);
        runner.setHeight("18px");

        // TODO make runners draggable using static method

        DragSource<Div> dragSource = DragSource.create(runner);
        dragSource.setEffectAllowed(EffectAllowed.MOVE);
        dragSource.addDragStartListener(this::onRunnerDragStart);

        return runner;
    }

    private void onRunnerDragStart(DragStartEvent<Div> divDragStartEvent) {
        divDragStartEvent.getComponent().getParent().ifPresent(component -> {
            boolean active = !component.equals(allRunnersLayout);
            DropTarget<VerticalLayout> dropTarget = DropTarget
                    .configure(allRunnersLayout, active);
        });
    }

    private void onRunnerDropBackToList(
            DropEvent<VerticalLayout> divDropEvent) {
        divDropEvent.getDragSourceComponent().ifPresent(allRunnersLayout::add);
    }

    private void resetTeams() {
        for (int i = 0; i < teamsField.getValue(); i++) {
            createTeam(i + 1);
        }
    }

    private void createTeam(int index) {
        VerticalLayout teamLayout = new VerticalLayout();
        teamLayout.setWidth("200px");
        teamLayout.getStyle().set("border", "1px solid black");
        teamLayout.add(new Html("<div><b>" + index + ". Team</b></div>"));

        int runners = runnersField.getValue().intValue();
        for (int i = 1; i <= runners; i++) {
            teamLayout.add(new TeamSlot(i));
        }

        teamsLayout.add(teamLayout);
    }

    public static class TeamSlot extends Div {

        private Component currentRunner;

        public TeamSlot(int index) {
            add(new Span(index + ".   "));
            setWidth("100%");
            setMinHeight(SLOT_HEIGHT);
            getStyle().set("border-bottom", "1px dotted black");
            getStyle().set("display", "inline-flex");

            // Making a component a drop target
            DropTarget<TeamSlot> dropTarget = DropTarget.create(this);
            dropTarget.setDropEffect(DropEffect.MOVE);
            dropTarget.addDropListener(event -> {
                event.getDragSourceComponent().ifPresent(droppedComponent -> {
                    if (currentRunner != null) {
                        swapDroppedAndCurrent(droppedComponent);
                    } else {
                        add(droppedComponent);
                    }
                    currentRunner = droppedComponent;
                });
            });
        }

        private void swapDroppedAndCurrent(Component droppedComponent) {
            droppedComponent.getParent().map(HasComponents.class::cast)
                    .ifPresent(parent -> parent.add(currentRunner));
            add(droppedComponent);
        }
    }
}
