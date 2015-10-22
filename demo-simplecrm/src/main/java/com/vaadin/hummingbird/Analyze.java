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

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.JavaScript;
import com.vaadin.hummingbird.addon.charts.ColumnChart;
import com.vaadin.ui.Template;

@HTML({"vaadin://bower_components/vaadin-charts/vaadin-funnel-chart.html",
	"vaadin://bower_components/vaadin-charts/vaadin-pie-chart.html"})
@JavaScript({"analyze.js"})
public class Analyze extends Template {

	private ColumnChart ageChart;
	
	public boolean initialized = false;
	@Override
	public void attach() {
		super.attach();
		if (!initialized) {
			this.getElement().getNode().enqueueRpc("createCharts($0,$1);", getGenderJSON(), getStatusJSON());
			initialized = true;
			ageChart.setTitle("Age");
			ageChart.createSeries(getAgesData(), "Ages");
		}
	}
	
	private Object getGenderJSON() {
		return "{\"Men\":40,\"Women\":60}";
	}

	private Object getStatusJSON() {
		return "{\"Imported lead\": 4, \"Not contacted\": 6, \"Contacted\": 8, \"Customer\": 5}";
	}

	private String getAgesJSON() {
		return "{\"0-15\":4,\"15-30\":3,\"30-60\":16,\"60-100\":7}";
	}
	// Or get from a database or whatever
	private HashMap getAgesData() {
		HashMap map = new HashMap();
		map.put("0-15", 4);
		map.put("15-30", 3);
		map.put("30-60", 16);
		map.put("60-100", 7);
		return map;
	}
}
