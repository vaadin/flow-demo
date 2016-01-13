package com.vaadin.hummingbird;

import com.vaadin.annotations.Tag;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;

@Tag("li")
public class TodoRow extends CssLayout {

    private CheckBox completed;
    private TextField edit;
    private HTML caption;

    private Todo todo;

    private CssLayout view = new CssLayout();

    public TodoRow() {
        view.addStyleName("view");
        addComponent(view);

        // addStyleName("todo-row");

        completed = new CheckBox(null);
        completed.setStyleName("toggle");
        view.addComponent(completed);

        caption = new HTML("<label />");
        view.addComponent(caption);

        Button destroy = new Button();
        destroy.addStyleName("destroy");
        addComponent(destroy);

        edit = new TextField();
        edit.setStyleName("edit");
        addComponent(edit);

        /* Event handling */
        completed.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                getPresenter().markCompleted(todo, completed.getValue());
            }
        });

        addLayoutClickListener(new LayoutClickListener() {
            @Override
            public void layoutClick(LayoutClickEvent event) {
                if (event.isDoubleClick()
                        && caption == event.getClickedComponent()) {
                    setEditing(true);
                }
            }
        });

        // Blur instead of ValueChange as we want to go back to non-edit mode
        // also when there are no changes
        edit.addBlurListener(new BlurListener() {
            @Override
            public void blur(BlurEvent event) {
                getPresenter().updateText(todo, edit.getValue());
                setEditing(false);
            }
        });

        destroy.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                getPresenter().delete(todo);
            }
        });

    }

    private void setEditing(boolean editing) {
        getElement().setClass("editing", editing);
        if (editing) {
            edit.selectAll();
            edit.focus();
        }
    }

    public TodoPresenter getPresenter() {
        return findAncestor(TodoViewImpl.class).getPresenter();
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
        setStyleName("completed", todo.isCompleted());
        completed.setValue(todo.isCompleted());
        caption.setInnerHtml(todo.getText());
        edit.setValue(todo.getText());
    }

    Todo getTodo() {
        return todo;
    }

}