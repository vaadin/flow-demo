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
package com.vaadin.hummingbird;

import java.util.HashMap;
import java.util.List;

import com.vaadin.annotations.Bower;
import com.vaadin.hummingbird.CrmUI.MyView;
import com.vaadin.hummingbird.kernel.ListNode;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Template;

@Bower({ "google-map" })
public class Map extends Template implements MyView {

    public interface MapLocation {
        public double getLat();

        public void setLat(double lat);

        public double getLon();

        public void setLon(double lon);

        public String getName();

        public void setName(String name);
    }

    public interface MapModel extends Model {
        public List<MapLocation> getLocations();

        public void setLocations(List<MapLocation> locations);
    }

    @Override
    public void attach() {
        super.attach();
        List<HashMap<String, Object>> locationData = ((SimpleCrmMain) getParent())
                .getCustomerData().getLocations();
        for (HashMap<String, Object> loc : locationData) {
            addLocation((Double) loc.get("lat"), (Double) loc.get("lon"),
                    (String) loc.get("name"));
        }
    }

    @Override
    protected MapModel getModel() {
        return (MapModel) super.getModel();
    }

    private List<MapLocation> getLocations() {
        return getModel().getLocations();

    }

    @Override
    protected void init() {
        getNode().put("locations", new ListNode());
    }

    private MapLocation addLocation(Double lat, Double lon, String name) {
        MapLocation loc = Model.create(MapLocation.class);
        loc.setLat(lat);
        loc.setLon(lon);
        loc.setName(name);
        List<MapLocation> locations = getLocations();
        locations.add(loc);
        return loc;
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    @Override
    public Template getTemplate() {
        return this;
    }
}
