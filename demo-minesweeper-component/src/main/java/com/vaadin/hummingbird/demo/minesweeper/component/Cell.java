/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.minesweeper.component;

import java.util.function.Consumer;

import com.vaadin.annotations.DomEvent;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.dom.EventRegistrationHandle;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentEvent;

@Tag("td")
public class Cell extends Component {

    private static final String CLASS_MARKED = "marked";
    private static final String CLASS_EMPTY = "empty";
    private static final String CLASS_MINE = "mine";

    @DomEvent("click")
    public static class CellClickEvent extends ComponentEvent {
        public CellClickEvent(Cell source, boolean fromClient) {
            super(source, fromClient);
        }

        @Override
        public Cell getSource() {
            return (Cell) super.getSource();
        }

    }

    public Cell() {
        getElement().addEventListener("contextmenu", e -> setMarked(true),
                "event.preventDefault()");
    }

    public void setMarked(boolean marked) {
        if (isRevealed()) {
            return;
        }

        getElement().getClassList().set(CLASS_MARKED, marked);
    }

    public boolean isMarked() {
        return hasClass(CLASS_MARKED);
    }

    public EventRegistrationHandle addCellClickListener(
            Consumer<CellClickEvent> listener) {
        return addListener(CellClickEvent.class, listener);
    }

    public boolean isRevealed() {
        return hasClass(CLASS_MINE) || hasClass(CLASS_EMPTY);
    }

    public int getCol() {
        return getElement().getParent().indexOfChild(getElement());
    }

    public int getRow() {
        return ((Row) getParent().get()).getRow();
    }

    public boolean isMine() {
        return hasClass(CLASS_MINE);
    }

    private boolean hasClass(String className) {
        return getElement().getClassList().contains(className);
    }

    public void setMine() {
        assert isEmpty();
        getElement().getClassList().add(CLASS_MINE);
    }

    public boolean isEmpty() {
        return hasClass(CLASS_EMPTY);
    }

    public void setEmpty() {
        assert !isMine();

        getElement().getClassList().add(CLASS_EMPTY);

    }

    public void setText(String text) {
        getElement().setTextContent(text);
    }

}
