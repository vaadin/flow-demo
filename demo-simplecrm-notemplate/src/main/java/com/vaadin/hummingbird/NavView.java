package com.vaadin.hummingbird;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class NavView extends HorizontalLayout {
	private VerticalLayout navbar = new VerticalLayout();
	public static final String ABOUT = "About";
	public static final String MAP = "Map";
	public static final String CUSTOMERS = "Customers";
	public static final String ANALYZE = "Analyze";

	public static final List<String> pages = new ArrayList<String>() {
		{
			add(ABOUT);
			add(MAP);
			add(CUSTOMERS);
			add(ANALYZE);
		}
	};
	public static final Map linkMap;

	static {
		Map<String, String> links = new HashMap<String, String>();
		links.put(ABOUT, "/Customers");
		links.put(MAP, "/Map");
		links.put(CUSTOMERS, "/Customers");
		links.put(ANALYZE, "/Analyze");
		linkMap = Collections.unmodifiableMap(links);
	}

	public NavView(AbstractComponent content) {
		super();
		pages.forEach((page) -> navbar.addComponent(new Label(page + ":" + linkMap.get(page) )));
		addComponent(navbar);
		addComponent(content);
	}

}