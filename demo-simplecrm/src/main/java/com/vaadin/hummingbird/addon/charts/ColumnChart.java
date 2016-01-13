package com.vaadin.hummingbird.addon.charts;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.annotations.HTML;
import com.vaadin.annotations.Tag;
import com.vaadin.ui.AbstractComponent;

@HTML("context://bower_components/vaadin-charts/vaadin-column-chart.html")
@Tag("vaadin-column-chart")
public class ColumnChart extends AbstractComponent {

    public void setTitle(String title) {
        this.getElement().getNode().enqueueRpc(
                "$0.chart.setTitle({text: \"" + title + "\"});",
                this.getElement());
    }

    public void setCategories(List<String> categories, int axisIndex) {
        this.getElement().getNode().enqueueRpc(
                "$0.chart.axes[$1].setCategories($2);", this.getElement(),
                axisIndex, categories.toArray());
    }

    public void addSeries(String name, List<Object[]> seriesData) {
        this.getElement().getNode().enqueueRpc(
                "$0.chart.addSeries({name:$1, data:$2})", this.getElement(),
                name, seriesData.toArray());
    }

    public void removeAllSeries() {
        this.getElement().getNode().enqueueRpc(
                "for (var i = 0; i < $0.chart.series.length; i++){ $0.chart.series[i].remove(); }",
                this.getElement());
    }

    public void createSeries(LinkedHashMap<String, Integer> data,
            String seriesName) {
        List<String> categories = new ArrayList<String>();
        List<Object[]> dataList = new ArrayList<Object[]>();
        for (String key : data.keySet()) {
            categories.add(key);
            List<Object> dataRow = new ArrayList<Object>();
            dataRow.add(key);
            dataRow.add(data.get(key));
            dataList.add(dataRow.toArray());
        }
        setCategories(categories, 0);
        addSeries(seriesName, dataList);
    }

}
