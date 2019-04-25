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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.EffectAllowed;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.demo.dnd.model.Card;

public class CardComponent extends Button implements DragSource<CardComponent> {

    private final Card card;

    public CardComponent(Card card) {
        this.card = card;

        initComponent();
        initDnD();
    }

    private void initComponent() {
        addClassName("card");

        getElement().appendChild(new H6(card.getName()).getElement());
        if (card.getDetails()!=null) {
            getElement().appendChild(new Span(card.getDetails()).getElement());
        }
    }

    private void initDnD() {
        setDraggable(true);
        setDragData(card);
        setEffectAllowed(EffectAllowed.MOVE);
    }

    @Override
    public CardComponent getDragSourceComponent() {
        return this;
    }
}
