package com.vaadin.hummingbird;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PaperButton;
import com.vaadin.ui.VerticalLayout;

public class About extends VerticalLayout {
	Label aboutContent = new Label("About page content...");

	public About() {
		super();
		addComponent(aboutContent);

	}
	 
}