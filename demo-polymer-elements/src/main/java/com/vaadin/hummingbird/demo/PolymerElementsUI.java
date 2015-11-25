package com.vaadin.hummingbird.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.iron.IronFlexLayout;
import com.vaadin.hummingbird.paper.PaperCard;
import com.vaadin.hummingbird.paper.PaperDropdownMenu;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperMenu;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Viewport("user-scalable=no,initial-scale=1.0")
public class PolymerElementsUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final IronFlexLayout ironFlexLayout = new IronFlexLayout();

        final PaperMenu paperMenu = new PaperMenu();
        paperMenu.getElement().addClass("dropdown-content");
        for (int i = 0; i < 10; i++) {
            PaperItem paperItem = new PaperItem();
            paperItem.getElement().setTextContent("Item " + i);
            paperMenu.addComponent(paperItem);
        }
        paperMenu.setWidth("200px");

        PaperCard paperCard = new PaperCard().setHeading("Events");

        paperMenu.addIronSelectListener(event -> {
            PaperItem paperItem = new PaperItem();
            paperItem.getElement()
                    .setTextContent("Item Selected: " + event.getItem());
            paperCard.addComponent(paperItem);
        });

        PaperDropdownMenu paperDropdownMenu = new PaperDropdownMenu()
                .setLabel("Paper Dropdown Menu");
        paperDropdownMenu.setWidth("400px");
        paperDropdownMenu.addComponent(paperMenu);
        paperDropdownMenu.addPaperDropdownOpenListener(event -> {
            PaperItem paperItem = new PaperItem();
            paperItem.getElement().setTextContent("DropDown opened");
            paperCard.addComponent(paperItem);
        });
        paperDropdownMenu.addPaperDropdownCloseListener(event -> {
            PaperItem paperItem = new PaperItem();
            paperItem.getElement().setTextContent("DropDown closed");
            paperCard.addComponent(paperItem);
        });

        ironFlexLayout.addComponent(paperDropdownMenu);
        ironFlexLayout.addComponent(paperCard);

        setContent(ironFlexLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PolymerElementsUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
