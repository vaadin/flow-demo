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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.JavaScript;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.ui.Template;

@JavaScript({"map.js","https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&callback=initialize"})
@Bower({"google-map"})
public class Map extends Template {

	public interface MapLocation {
        public double getLat();
        
        public void setLat(double lat);
        
        public double getLon();
        
        public void setLon(double lon);
    }

    public interface MapModel extends Model {
        public List<MapLocation> getLocations();

        public void setLocations(List<MapLocation> locations);
    }
    
    public Map() {
    	getNode().getMultiValued("locations");
    }
    
    @Override
    public void attach() {
       	super.attach();
       	List<HashMap<String, Double>> locations = ((SimpleCrmMain) getParent()).getCustomerData().getLocations();
       	ArrayList<Double> lats = new ArrayList<Double>();
       	ArrayList<Double> lons = new ArrayList<Double>();
       	for (HashMap<String, Double> loc : locations) {
       		addLocation(loc.get("lat"), loc.get("lon"));
       		lats.add(loc.get("lat"));
       		lons.add(loc.get("lon"));
       	}
       	Element mapElem = this.getElementById("customer-map");
       	this.getElement().getNode().enqueueRpc("createMarkers($0,$1,$2)",lats.toArray(),lons.toArray(),mapElem);
    }
    
    @Override
    protected MapModel getModel() {
    	return (MapModel) super.getModel();
    }
    
    private List<MapLocation> getLocations() {
    	List<MapLocation> locations = getModel().getLocations();
    	if (locations == null) {
    		locations = new ArrayList<MapLocation>();
    	}
    	return locations;
    }
    
    private MapLocation addLocation(Double lat,Double lon) {
    	MapLocation loc = Model.create(MapLocation.class);
    	loc.setLat(lat);
    	loc.setLon(lon);
    	List<MapLocation> locations = getLocations();
    	locations.add(loc);
    	return loc;
    }
}
