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
import java.util.Iterator;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.CrmUI.MyView;
import com.vaadin.hummingbird.addon.charts.ColumnChart;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Template;

import elemental.json.Json;
import elemental.json.JsonArray;

@HTML({ "vaadin://bower_components/vaadin-charts/vaadin-funnel-chart.html",
        "vaadin://bower_components/vaadin-charts/vaadin-pie-chart.html" })
@JavaScript({ "analyze.js" })
public class Analyze extends Template implements MyView {
    public interface AnalyzeModel extends Model {
        public void setStatusData(JsonArray statusData);
    }

    private ColumnChart ageChart;

    @Override
    protected AnalyzeModel getModel() {
        return (AnalyzeModel) super.getModel();
    }

    @TemplateEventHandler
    public void updateAgeChart() {
        ageChart.setTitle("Age");
        ageChart.removeAllSeries();
        ageChart.createSeries(getCustomerData().getAgesCounts(), "Ages");
    }

    @TemplateEventHandler
    public void updateGenderChart() {
        this.getElement().getNode().enqueueRpc("updateGenterChart($0,$1);",
                getGenderJSON(), getElementById("gender-chart"));
    }

    private Object getGenderJSON() {
        return toJson(getCustomerData().getGenderCounts());
    }

    private JsonArray getStatusJSON() {
        JsonArray json = Json.createArray();
        getCustomerData().getStatusCounts().forEach((key, count) -> {
            JsonArray row = Json.createArray();
            row.set(0, key);
            row.set(1, count.intValue());
            json.set(json.length(), row);
        });

        return json;
    }

    private String toJson(HashMap<String, Integer> values) {
        StringBuffer json = new StringBuffer("{");
        Iterator<String> iter = values.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            json.append('"').append(key).append("\":").append(values.get(key));
            if (iter.hasNext()) {
                json.append(',');
            }
        }
        json.append("}");
        return json.toString();
    }

    private CustomerData getCustomerData() {
        SimpleCrmMain main = ((SimpleCrmMain) getParent());
        return main.getCustomerData();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        getModel().setStatusData(getStatusJSON());
    }

    @Override
    public Template getTemplate() {
        return this;
    }
}
