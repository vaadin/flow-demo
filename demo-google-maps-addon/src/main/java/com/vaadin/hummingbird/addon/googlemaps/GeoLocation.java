package com.vaadin.hummingbird.addon.googlemaps;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.EventParameter;
import com.vaadin.annotations.EventType;
import com.vaadin.annotations.Tag;
import com.vaadin.event.ElementEvents;
import com.vaadin.event.EventListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

@Bower("geo-location")
@Tag("geo-location")
public class GeoLocation extends AbstractComponent {

    public GeoLocation() {
    }

    @EventType("geo-response")
    public static class GeoResponseEvent extends Component.Event {
        @EventParameter
        private double longitude;
        @EventParameter
        private double latitude;

        public GeoResponseEvent(Component source, double latitude,
                double longitude) {
            super(source);
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public void addGeoResponseListener(
            EventListener<GeoResponseEvent> listener) {
        ElementEvents.addElementListener(this, GeoResponseEvent.class,
                listener);
    }
}