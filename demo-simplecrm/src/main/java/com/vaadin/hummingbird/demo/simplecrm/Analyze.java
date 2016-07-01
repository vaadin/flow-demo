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

import java.util.HashMap;
import java.util.Iterator;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Id;
import com.vaadin.annotations.JavaScript;
import com.vaadin.hummingbird.demo.simplecrm.addon.charts.ColumnChart;
import com.vaadin.hummingbird.demo.simplecrm.data.CustomerService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

import elemental.json.Json;
import elemental.json.JsonArray;

@HtmlImport("context://bower_components/vaadin-charts/vaadin-funnel-chart.html")
@HtmlImport("context://bower_components/vaadin-charts/vaadin-pie-chart.html")
@JavaScript("analyze.js")
public class Analyze extends Template {
    public interface AnalyzeModel extends TemplateModel {
        public void setStatusData(JsonArray statusData);
    }

    private ColumnChart ageChart;

    @Id("gender-chart")
    private Element genderChart;

    @Override
    protected AnalyzeModel getModel() {
        return (AnalyzeModel) super.getModel();
    }

    @EventHandler
    public void updateAgeChart() {
        ageChart.setTitle("Age");
        ageChart.removeAllSeries();
        ageChart.createSeries(getCustomerData().getAgesCounts(), "Ages");
    }

    @EventHandler
    public void updateGenderChart() {
        getUI().get().getPage().executeJavaScript("updateGenterChart($0,$1);",
                getGenderJSON(), genderChart);
    }

    private String getGenderJSON() {
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

    private CustomerService getCustomerData() {
        SimpleCrmMain main = ((SimpleCrmMain) getParent().get());
        return main.getCustomerData();
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        super.onLocationChange(locationChangeEvent);
        getModel().setStatusData(getStatusJSON());
    }

}
