package com.vaadin.hummingbird;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.addon.googlemaps.GeoLocation;
import com.vaadin.hummingbird.addon.googlemaps.GoogleMap;
import com.vaadin.hummingbird.addon.googlemaps.GoogleMap.GoogleMapMarker;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Viewport("user-scalable=no,initial-scale=1.0")
public class MyUI extends UI {

    private VerticalLayout log;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout l = new HorizontalLayout();
        log = new VerticalLayout();
        Label spacer = new Label("");
        log.addComponent(spacer);
        log.setExpandRatio(spacer, 1);
        GoogleMap maps = new GoogleMap();
        maps.setWidth("600px");
        maps.setHeight("600px");
        maps.addDoubleClickListener(e -> {
            log("Double clicked at " + e.getLatitude() + ", " + e.getLongitude()
                    + " (map x,y: " + e.getPixelX() + "," + e.getPixelY()
                    + ")");
        });
        GoogleMapMarker vaadinHqMarker = GoogleMap.VAADIN_HQ();
        vaadinHqMarker.addClickListener(e -> {
            log("Clicked on HQ marker");
        });
        maps.addMarker(vaadinHqMarker);

        GeoLocation loc = new GeoLocation();
        loc.addGeoResponseListener(e -> {
            log("Geo response: " + e.getLatitude() + "," + e.getLongitude());
            maps.setLatitude(e.getLatitude());
            maps.setLongitude(e.getLongitude());
        });

        l.addComponents(maps, loc, log);
        setContent(l);
    }

    private void log(String msg) {
        log.addComponentAsFirst(new Label(msg));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
