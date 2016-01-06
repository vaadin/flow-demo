package com.vaadin.hummingbird.demo.paper;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.SampleBase;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperProgress;
import com.vaadin.hummingbird.polymer.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;

@PolymerStyle("progress-styles")
public class ProgressSample extends SampleBase {

    public ProgressSample() {
        CssLayout root = layout("vertical layout");
        addComponent(root);
        addStyleName("progress-sample");

        addBasic(root);
        addIntermidiate(root);
        addColor(root);
    }

    private void addBasic(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Progress bar");

        PaperProgress progress = new PaperProgress().setValue(0);
        section.addComponents(progress,
                new PaperButton().setRaised(true).setTextContent("Start")
                        .withClickListener(e -> startStop(e, progress)));

    }

    private boolean started;

    private void startStop(ClickEvent<PaperButton> event,
            PaperProgress progress) {
        if (progress.getValue() >= 100.0D) {
            progress.setValue(0D);
        }
        started = !started;
        if (started) {
            PaperButton paperButton = event.getPolymerComponent()
                    .setTextContent("Stop");
            new Thread(() -> {
                while (started) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                    }
                    UI ui = paperButton.getUI();
                    if (started && paperButton.isAttached() && ui != null) {
                        double value = progress.getValue() + 8D;
                        ui.access(() -> progress.setValue(value));
                        if (value < 100.0D) {
                        } else {
                            started = false;
                            ui.access(() -> paperButton
                                    .setTextContent("Finished - Restart"));
                        }
                    } else {
                        started = false;
                    }
                }
            }).start();
        } else {
            event.getPolymerComponent().setTextContent("Stopped - Continue");
        }
    }

    private void addIntermidiate(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Intermediate");

        section.addComponents(new PaperProgress().setIndeterminate(true), br(),
                new PaperProgress().withClassName("blue").setIndeterminate(true)
                        .setValue(800).setMin(100).setMax(100),
                br());

    }

    private void addColor(CssLayout root) {
        CssLayout section = createVerticalSection(root, "Color");

        section.addComponents(
                new PaperProgress().setValue(40).withClassName("blue"), br(),
                new PaperProgress().setMin(100).setMax(1000).setValue(800)
                        .withClassName("red"),
                br(), new PaperProgress().setValue(40).withClassName("orange"),
                br(),
                new PaperProgress().setMax(200).setValue(200)
                        .withClassName("green"),
                br(), new PaperProgress().setSecondaryProgress(80).setValue(40)
                        .withClassName("blue"),
                br());
    }

}
