package com.vaadin.hummingbird.addon.googlemaps;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.event.ComponentEventListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;

@HTML("vaadin://bower_components/geo-location/geo-location.html")
@Tag("geo-location")
public class GeoLocation extends AbstractComponent {

    public GeoLocation() {
    }

    public static class GeoResponseEvent extends Component.Event {
        private double longitude;
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

    public interface GeoResponseListener extends ComponentEventListener {
        public void locationUpdate(GeoResponseEvent event);
    }

    public void addGeoResponseListener(GeoResponseListener listener) {
        if (!hasListeners(GeoResponseListener.class)) {
            getElement().addEventData("geo-response", "element.latitude",
                    "element.longitude");
            getElement().addEventListener("geo-response", e -> {
                fireEvent(new GeoResponseEvent(this,
                        e.getNumber("element.latitude"),
                        e.getNumber("element.longitude")));
            });
        }
        super.addListener(GeoResponseListener.class, listener);
    }
}