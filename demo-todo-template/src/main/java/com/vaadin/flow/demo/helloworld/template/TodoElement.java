package com.vaadin.flow.demo.helloworld.template;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * Created by Mikael on 21/02/17.
 */
@Tag("todo-element")
@HtmlImport("frontend://components/TodoElement.html")
public class TodoElement extends PolymerTemplate<TodoElement.TodoModel> {

    List<Runnable> changeListeners = new ArrayList<>(0);

    public interface TodoModel extends TemplateModel {
        String getTask();

        void setTask(String task);

        String getUser();

        void setUser(String user);

        Number getRid();

        void setRid(Number rid);

        String getTime();

        void setTime(String time);

        boolean isCompleted();

        void setCompleted(boolean completed);
    }

    private Todo todo;

    public TodoElement(Todo todo) {
        this.todo = todo;

        getModel().setTask(todo.getTask());
        getModel().setUser(todo.getUser());
        getModel().setRid(todo.getRid());
        getModel().setTime(todo.getTime()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")));

        getElement().addPropertyChangeListener("completed",
                event -> taskCompleted());
        getElement().addPropertyChangeListener("task",
                event -> todo.setTask(getModel().getTask()));
    }

    private void taskCompleted() {
        todo.setCompleted(getModel().isCompleted());

        changeListeners.forEach(Runnable::run);
    }

    /**
     * Get the {@link Todo} item for this TodoElement.
     * 
     * @return todo item
     */
    public Todo getTodo() {
        return todo;
    }

    /**
     * Returns completion state of this {@link Todo} item.
     * 
     * @return
     */
    public boolean isCompleted() {
        return getModel().isCompleted();
    }

    /**
     * Add a state change listener that is informed when the completed state
     * changes.
     * 
     * @param listener
     *            runnable method to be used as a listener
     */
    public void addStateChangeListener(Runnable listener) {
        changeListeners.add(listener);
    }
}
