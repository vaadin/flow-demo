package com.vaadin.hummingbird.addon.googlemaps;

import java.util.EventObject;

import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.event.EventListener;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

@HTML("vaadin://bower_components/google-map/google-map.html")
@Tag("google-map")
public class GoogleMap extends AbstractComponent {

    public static GoogleMapMarker VAADIN_HQ() {
        return new GoogleMapMarker(60.44947216379834, 22.300937175750732,
                "Vaadin HQ");
    };

    public GoogleMap() {
        setWidth("300px");
        setHeight("300px");
    }

    public double getLatitude() {
        return getElement().getAttribute("latitude", -1);
    }

    public void setLatitude(double latitude) {
        getElement().setAttribute("latitude", latitude);
    }

    public double getLongitude() {
        return getElement().getAttribute("longitude", -1);
    }

    public void setLongitude(double longitude) {
        getElement().setAttribute("longitude", longitude);
    }

    public void addMarker(GoogleMapMarker marker) {
        getElement().appendChild(marker.getElement());
    }

    public void removeMarker(GoogleMapMarker marker) {
        getElement().removeChild(marker.getElement());
    }

    public static class MouseEvent extends Component.Event {
        public MouseEvent(GoogleMap source) {
            super(source);
        }

        @EventParameter("detail.latLng.G")
        private double latitude;
        @EventParameter("detail.latLng.G")
        private double longitude;

        @EventParameter("detail.pixel.x")
        private int pixelX;
        @EventParameter("detail.pixel.y")
        private int pixelY;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public int getPixelX() {
            return pixelX;
        }

        public int getPixelY() {
            return pixelY;
        }
    }

    @EventType("google-map-dblclick")
    public static class DoubleClickEvent extends MouseEvent {
        public DoubleClickEvent(GoogleMap source) {
            super(source);
        }
    }

    @EventType("google-map-click")
    public static class ClickEvent extends MouseEvent {
        public ClickEvent(GoogleMap source) {
            super(source);
        }
    }

    public static class GoogleMapMarker {

        @EventType("google-map-marker-click")
        public static class ClickEvent extends EventObject {
            @EventParameter("detail.latLng.G")
            private double latitude;
            @EventParameter("detail.latLng.G")
            private double longitude;

            public ClickEvent(GoogleMapMarker source) {
                super(source);
            }
        }

        private final Element element = new Element("google-map-marker");

        public Element getElement() {
            return element;
        }

        public GoogleMapMarker(double latitude, double longitude,
                String title) {
            getElement().setAttribute("latitude", latitude);
            getElement().setAttribute("longitude", longitude);
            getElement().setAttribute("title", title);
        }

        public void addClickListener(EventListener<ClickEvent> listener) {
            getElement().setAttribute("clickEvents", true);
            getElement().addEventListener(ClickEvent.class, listener, this);
        }

        public void removeClickListener(EventListener<ClickEvent> listener) {
            getElement().removeEventListener(ClickEvent.class, listener, this);
        }
    }

    public void addDoubleClickListener(
            EventListener<DoubleClickEvent> listener) {
        getElement().setAttribute("clickEvents", true);
        getElement().addEventListener(DoubleClickEvent.class, listener, this);
    }

    public void removeDoubleClickListener(
            EventListener<DoubleClickEvent> listener) {
        getElement().removeEventListener(DoubleClickEvent.class, listener,
                this);

    }
}
