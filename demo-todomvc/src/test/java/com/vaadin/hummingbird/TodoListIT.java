package com.vaadin.hummingbird;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.testbench.By;

import org.junit.Assert;
import org.junit.Test;

public class TodoListIT extends AbstractTestBenchTest {

    @Test
    public void testInitialScreen() {
        openUrl();
        verifyInitialScreenContents();
    }

    @Test
    public void testPrerenderScreen() {
        openUrl("pre-only");
        verifyInitialScreenContents();
    }

    private void verifyInitialScreenContents() {
        List<WebElement> todos = getTodos();

        Assert.assertEquals(1, getItemsLeft());
        Assert.assertFalse(isClearCompletedVisible());

        Assert.assertEquals(1, todos.size());

        WebElement todo = todos.get(0);
        Assert.assertEquals("Make hummingbird work", getTodoText(todo));
        Assert.assertFalse(isTodoCompleted(todo));
    }

    @Test
    public void testEditTodo() {
        openUrl();
        WebElement todo = getTodos().get(0);

        new Actions(getDriver())
                .doubleClick(todo.findElement(By.tagName("label"))).build()
                .perform();

        Assert.assertTrue(isTodoEditing(todo));

        WebElement editInput = todo.findElement(By.cssSelector("input.edit"));
        // For some reason this adds the text to the end of the input
        editInput.sendKeys(" edited\n");

        Assert.assertFalse(isTodoEditing(todo));

        Assert.assertEquals("Make hummingbird work edited", getTodoText(todo));
    }

    @Test
    public void testRemoveTodo() {
        openUrl();
        WebElement todo = getTodos().get(0);

        // Hover input to reveal destroy button
        new Actions(getDriver())
                .moveToElement(todo.findElement(By.tagName("label"))).build()
                .perform();
        todo.findElement(By.cssSelector("button.destroy")).click();

        Assert.assertEquals(0, getItemsLeft());
        Assert.assertEquals(0, getTodos().size());
    }

    @Test
    public void testCompleteTodo() {
        openUrl();
        WebElement todo = getTodos().get(0);
        todo.findElement(By.cssSelector("input.toggle")).click();

        Assert.assertTrue(isTodoCompleted(todo));
        Assert.assertEquals(0, getItemsLeft());
        Assert.assertTrue(isClearCompletedVisible());

        todo.findElement(By.cssSelector("input.toggle")).click();
        Assert.assertFalse(isTodoCompleted(todo));
        Assert.assertEquals(1, getItemsLeft());
        Assert.assertFalse(isClearCompletedVisible());
    }

    @Test
    public void testAddTodo() throws InterruptedException, IOException {
        openUrl();
        WebElement newTodoField = findElement(By.id("new-todo"));

        newTodoField.sendKeys("New todo\n");

        Assert.assertEquals(2, getItemsLeft());

        List<WebElement> todos = getTodos();
        Assert.assertEquals(2, todos.size());

        WebElement todo = todos.get(1);
        Assert.assertEquals("New todo", getTodoText(todo));
        Assert.assertFalse(isTodoCompleted(todo));
    }

    @Test
    public void completeAll() {
        openUrl();
        // Add another todo
        findElement(By.id("new-todo")).sendKeys("New todo\n");

        findElement(By.id("toggle-all")).click();

        Assert.assertTrue(isTodoCompleted(getTodos().get(0)));
        Assert.assertTrue(isTodoCompleted(getTodos().get(1)));
        Assert.assertEquals(0, getItemsLeft());
        Assert.assertTrue(isClearCompletedVisible());

        findElement(By.id("toggle-all")).click();

        Assert.assertFalse(isTodoCompleted(getTodos().get(0)));
        Assert.assertFalse(isTodoCompleted(getTodos().get(1)));
        Assert.assertEquals(2, getItemsLeft());
        Assert.assertFalse(isClearCompletedVisible());
    }

    @Test
    public void clearCompleted() {
        openUrl();
        // Add another todo
        findElement(By.id("new-todo")).sendKeys("New todo\n");

        // Mark initial as completed
        getTodos().get(0).findElement(By.cssSelector("input.toggle")).click();

        findElement(By.cssSelector(".clear-completed")).click();

        Assert.assertEquals(1, getTodos().size());
        Assert.assertEquals("New todo", getTodoText(getTodos().get(0)));
        Assert.assertFalse(isClearCompletedVisible());
    }

    private boolean isTodoEditing(WebElement todo) {
        return todo.getAttribute("class").contains("editing");
    }

    private boolean isTodoCompleted(WebElement todo) {
        return todo.getAttribute("class").contains("completed");
    }

    private String getTodoText(WebElement todo) {
        return todo.findElement(By.tagName("label")).getText();
    }

    private List<WebElement> getTodos() {
        List<WebElement> todoLis = findElements(
                By.cssSelector("ul.todo-list > li"));
        return todoLis;
    }

    private boolean isClearCompletedVisible() {
        WebElement clearCompleted = findElement(
                By.cssSelector(".clear-completed"));
        return !clearCompleted.getAttribute("class").contains("hidden");
    }

    private int getItemsLeft() {
        WebElement todoCount = findElement(
                By.cssSelector(".todo-count > strong"));
        String text = todoCount.getText();
        if (text.isEmpty()) {
            return 0;
        }

        int itemsLeft = Integer.parseInt(text);
        Assert.assertNotEquals(
                "x items left should not be shown at all for x = 0", 0,
                itemsLeft);
        return itemsLeft;
    }

}
