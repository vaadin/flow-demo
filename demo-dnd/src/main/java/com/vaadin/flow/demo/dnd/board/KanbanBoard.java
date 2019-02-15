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

package com.vaadin.flow.demo.dnd.board;

import java.util.Arrays;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dnd.DragEndEvent;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DragStartEvent;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropEvent;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.dnd.MainLayout;
import com.vaadin.flow.demo.dnd.model.Card;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@RouteAlias(value = "", layout = MainLayout.class)
@Route(value = "kanban", layout = MainLayout.class)
public class KanbanBoard extends FlexLayout {

    public static final String HIGHLIGHTED = "highlighted";

    public KanbanBoard() {
        super();
        getStyle().set("flex-wrap", "wrap");

        Arrays.stream(Card.Status.values()).forEach(this::createNewColumn);

        getColumn(Card.Status.INBOX).add(new NewCard(this::addNewCard));

        addInitialData();
    }

    private void createNewColumn(Card.Status status) {
        VerticalLayout container = createColumnContainer();
        container.add(new H4(status.toString().replace("_", " ")));
        setColumnStatus(container, status);

        DropTarget<VerticalLayout> dropTarget = DropTarget.create(container);
        // By default we don't allow any drops
        dropTarget.setDropEffect(DropEffect.NONE);
        dropTarget.addDropListener(this::onDrop);

        add(container);
    }

    private void setColumnStatus(Component container, Card.Status status) {
        ComponentUtil.setData(container, Card.Status.class, status);
    }

    private Card.Status getColumnStatus(Component component) {
        return ComponentUtil.getData(component, Card.Status.class);
    }

    private VerticalLayout getColumn(Card.Status status) {
        return (VerticalLayout) getChildren()
                .filter(component -> getColumnStatus(component) == status)
                .findFirst().orElseThrow(() -> new IllegalStateException(
                        "Could not find correct status column"));
    }

    private VerticalLayout createColumnContainer() {
        VerticalLayout container = new VerticalLayout();
        container.setMargin(true);
        container.addClassName("column");
        container.setWidth("300px");
        container.setMinHeight("600px");
        return container;
    }

    private void onDrop(DropEvent<VerticalLayout> dropEvent) {
        if (dropEvent.getDragSourceComponent().isPresent()) {
            VerticalLayout targetColumn = dropEvent.getComponent();
            Component sourceCard = dropEvent.getDragSourceComponent().get();

            dropEvent.getDragData().ifPresent(object -> {
                Card card = (Card) object;
                card.setStatus(getColumnStatus(targetColumn));
                if (card.getStatus() == Card.Status.DONE) {
                    // don't allow dnd for done cards
                    DragSource.configure(sourceCard, false);
                }
            });

            targetColumn.add(sourceCard);
        }
    }

    private void addNewCard(Card card) {
        CardComponent cardComponent = new CardComponent(card);
        getColumn(Card.Status.INBOX).add(cardComponent);
        cardComponent.addDragStartListener(this::onDragStart);
        cardComponent.addDragEndListener(this::onDragEnd);
    }

    private void onDragStart(DragStartEvent<CardComponent> dragStartEvent) {
        Card card = (Card) dragStartEvent.getComponent().getDragData();
        // iterate all columns and mark acceptable drop locations
        getChildren().forEach(column -> {
            Card.Status columnStatus = getColumnStatus(column);
            boolean validTransition = columnStatus
                    .isValidTransition(card.getStatus());
            DropTarget<Component> dropTarget = DropTarget.configure(column,
                    validTransition);
            column.getElement().getClassList().set(HIGHLIGHTED,
                    validTransition);
            if (validTransition) {
                dropTarget.setDropEffect(DropEffect.MOVE);
            }
        });
    }

    private void onDragEnd(DragEndEvent<CardComponent> dragEndEvent) {
        getChildren().forEach(column -> {
            DropTarget.configure(column, false).setDropEffect(DropEffect.NONE);
            column.getElement().getClassList().set(HIGHLIGHTED, false);
        });
    }

    private void addInitialData() {
        Card card = new Card("Sun is shining", "It must be Spring",
                Card.Status.INBOX);
        addNewCard(card);
    }

}
