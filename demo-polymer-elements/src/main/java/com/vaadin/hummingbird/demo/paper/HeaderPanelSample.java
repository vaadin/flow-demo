package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.kernel.Element;
import com.vaadin.hummingbird.paper.PaperHeaderPanel;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

@PolymerStyle("header-panel-styles")
public class HeaderPanelSample extends SampleBase {

    public HeaderPanelSample() {
        addStyleName("header-panel-sample");

        CssLayout root = layout("layout wrap inline center-center");
        addComponent(root);

        addComponent(new PaperHeaderPanel().withClassName("blue").with(
                element("div", "paper-header", "standard"),
                element("div", "content")));

        addComponent(
                new PaperHeaderPanel().setMode("seamed").withClassName("red")
                        .with(element("div", "paper-header", "seamed"),
                                element("div", "content")));

        addComponent(
                new PaperHeaderPanel().setMode("scroll").withClassName("orange")
                        .with(element("div", "paper-header", "scroll"),
                                element("div", "content")));

        addComponent(new PaperHeaderPanel().setMode("waterfall")
                .withClassName("green")
                .with(element("div", "paper-header", "waterfall"),
                        element("div", "content")));

        addComponent(new PaperHeaderPanel().setMode("waterfall-tall")
                .withClassName("pink")
                .with(element("div", "paper-header", "waterfall-tall"),
                        element("div", "content")));

        CssLayout header = layout("paper-header");
        header.addComponent(new HTML("</br>"));
        header.getElement()
                .appendChild(Element.createText("tall-class: medium-tall"));

        addComponent(new PaperHeaderPanel().setMode("waterfall-tall")
                .setTallClass("medium-tall").withClassName("cyan")
                .with(header, element("div", "content")));

        addComponent(
                new PaperHeaderPanel().setMode("cover").withClassName("lime")
                        .with(element("div", "paper-header tall", "cover"),
                                element("div", "content cover")));
    }
}
