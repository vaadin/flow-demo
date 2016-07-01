/*
 * Copyright 2000-2014 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird.demo.simplecrm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.hummingbird.demo.simplecrm.data.Customer;
import com.vaadin.hummingbird.demo.simplecrm.data.CustomerService;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.AttachEvent;
import com.vaadin.ui.Template;

@HtmlImport("context://bower_components/google-map/google-map.html")
public class Map extends Template {

    public static class MapLocation {
        private String name;
        private double lat, lon;

        public MapLocation(double lat, double lon, String name) {
            this.lat = lat;
            this.lon = lon;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }

    public interface MapModel extends TemplateModel {
        public List<MapLocation> getLocations();

        public void setLocations(List<MapLocation> locations);
    }

    public Map() {
        // TODO is this needed?
        getModel().setLocations(new ArrayList<>());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        Stream<Customer> customers = CustomerService.get().getCustomers();
        List<MapLocation> locations = getModel().getLocations();
        locations.clear();
        customers
                .map(customer -> new MapLocation(customer.getLat(),
                        customer.getLon(),
                        customer.getLastName() + ", "
                                + customer.getFirstName()))
                .forEach(locations::add);
    }

    @Override
    protected MapModel getModel() {
        return (MapModel) super.getModel();
    }

}
