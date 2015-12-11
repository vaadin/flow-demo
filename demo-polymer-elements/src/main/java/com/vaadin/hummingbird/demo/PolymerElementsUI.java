package com.vaadin.hummingbird.demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.hummingbird.demo.paper.ButtonSample;
import com.vaadin.hummingbird.demo.paper.CheckboxSample;
import com.vaadin.hummingbird.demo.paper.DialogSample;
import com.vaadin.hummingbird.iron.IronCollapse;
import com.vaadin.hummingbird.iron.IronFlexLayout;
import com.vaadin.hummingbird.iron.IronIcon;
import com.vaadin.hummingbird.iron.IronIcons;
import com.vaadin.hummingbird.iron.IronSelector;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperDialogScrollable;
import com.vaadin.hummingbird.paper.PaperDrawerPanel;
import com.vaadin.hummingbird.paper.PaperFab;
import com.vaadin.hummingbird.paper.PaperHeaderPanel;
import com.vaadin.hummingbird.paper.PaperIconButton;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperRipple;
import com.vaadin.hummingbird.paper.PaperStyles;
import com.vaadin.hummingbird.paper.PaperToolbar;
import com.vaadin.hummingbird.polymer.PolymerComponent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@com.vaadin.annotations.HTML({ "vaadin://themes/polymer/dialog-styles.html",
        "vaadin://themes/polymer/checkbox-styles.html",
        "vaadin://themes/polymer/button-styles.html",
        "vaadin://bower_components/paper-styles/demo-pages.html",
        "vaadin://bower_components/neon-animation/neon-animation.html" })
@Theme("polymer-demo")
@Viewport("user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1")
public class PolymerElementsUI extends UI {

    private CssLayout sidebarListPanel;
    private Label mainHeaderLabel;
    private PaperButton helpButton;
    private PaperButton javaButton;
    private CssLayout contentLayout;
    private PaperDialog aboutDialog;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        CssLayout drawer = new CssLayout();
        drawer.getElement().setAttribute("attr.drawer", true);
        drawer.addComponent(initSidebar());

        CssLayout main = new CssLayout();
        main.getElement().setAttribute("attr.main", true);
        main.addComponent(initMainView());

        final PaperDrawerPanel paperDrawerPanel = new PaperDrawerPanel()
                .setDrawerWidth("250px").with(drawer, main);

        CssLayout cssLayout = new CssLayout();
        cssLayout.addComponents(createPolymerElementDependantComponents());
        cssLayout.addComponents(paperDrawerPanel, createAboutDialog());
        setContent(cssLayout);

        addSamples();
    }

    private void addSamples() {
        final IronCollapse paperCollapse = addCategory("paper",
                "Paper Elements");
        paperCollapse.with(addSample("Button", false),
                addSample("CheckBox", false), addSample("Dialog", false),
                addSample("DropdownMenu"), addSample("Floating Button"),
                addSample("Header Panel"), addSample("Icon Button"),
                addSample("Item"), addSample("Input"), addSample("Material"),
                addSample("Menu"), addSample("Progress"),
                addSample("Radio Button"), addSample("Radio Group"),
                addSample("Ripple"), addSample("Spinner"), addSample("Slider"),
                addSample("Tabs"), addSample("Toast"),
                addSample("Toggle Button"), addSample("Toolbar"));

        addCategory("iron", "Iron Elements").with(addSample("Collapse"),
                addSample("Image"), addSample("List"), addSample("Selector"));

        // addCategory("vaadin", "Vaadin Elements")
        // .with(addSample("Grid", "VaadinGridSample"));

    }

    private IronCollapse addCategory(String path, String name) {
        final IronCollapse collapse = new IronCollapse();

        PaperItem item = new PaperItem().withClassName("category")
                .with(new IronIcon().setIconPolymer("expand-more"),
                        createSpanElement(name), new PaperRipple())
                .withClickListener(
                        event -> collapse.setOpened(!collapse.isOpened()));

        IronSelector selector = new IronSelector();
        collapse.with(selector);

        sidebarListPanel.addComponents(item, collapse);

        return collapse;
    }

    private PaperItem addSample(String name) {
        return addSample(name, true);
    }

    private PaperItem addSample(String name, boolean disabled) {
        return new PaperItem().setDisabled(disabled)
                .with(createSpanElement(name))
                .withClickListener(event -> openSample(name));
    }

    private void openSample(String sampleName) {
        contentLayout.removeAllComponents();
        contentLayout.addComponent(createSample(sampleName));
        mainHeaderLabel.setValue(sampleName);
    }

    private Component createSample(String sampleName) {
        switch (sampleName) {
        case "Button":
            return new ButtonSample();
        case "CheckBox":
            return new CheckboxSample();
        case "Dialog":
            return new DialogSample();
        // case "DropdownMenuSample": return new DropdownMenuSample();
        // case "FabSample": return new FabSample();
        // case "HeaderPanelSample": return new HeaderPanelSample();
        // case "IconButtonSample": return new IconButtonSample();
        // case "ItemSample": return new ItemSample();
        // case "InputSample": return new InputSample();
        // case "MaterialSample": return new MaterialSample();
        // case "MenuSample": return new MenuSample();
        // case "ProgressSample": return new ProgressSample();
        // case "RadioButtonSample": return new RadioButtonSample();
        // case "RadioGroupSample": return new RadioGroupSample();
        // case "RippleSample": return new RippleSample();
        // case "SpinnerSample": return new SpinnerSample();
        // case "SliderSample": return new SliderSample();
        // case "TabsSample": return new TabsSample();
        // case "ToastSample": return new ToastSample();
        // case "ToggleButtonSample": return new ToggleButtonSample();
        // case "ToolbarSample": return new ToolbarSample();
        // case "IronCollapseSample": return new IronCollapseSample();
        // case "IronImageSample": return new IronImageSample();
        // case "IronListSample": return new IronListSample();
        // case "IronSelectorSample": return new IronSelectorSample();
        // case "VaadinGridSample": return new VaadinGridSample();
        default:
            return new Label("Sample " + sampleName + " not yet implemented");
        }
    }

    private PolymerComponent<?>[] createPolymerElementDependantComponents() {
        return new PolymerComponent[] { new PaperStyles(), new IronIcons(),
                new IronFlexLayout() };
    }

    private Component initSidebar() {
        PaperHeaderPanel header = new PaperHeaderPanel().setMode("seamed");
        sidebarListPanel = new CssLayout();
        sidebarListPanel.addStyleName("list");

        header.addComponents(
                new PaperToolbar().withClassName("toolbar")
                        .with(new PaperFab().setIconPolymer("polymer")
                                .setMini(true).withClassName("iconpolymer")
                                .setElevation("1").setAttribute("onclick",
                                        "javascript:window.open('https://www.polymer-project.org/','_blank');"),
                new PaperFab()
                        .setSrc("http://vaadin.github.io/gwt-polymer-elements/demo/img/gwt.png")
                        .setMini(true).withClassName("icongwt")
                        .setElevation("1").setAttribute("onclick",
                                "javascript:window.open('http://gwtproject.org','_blank');"),
                new PaperFab()
                        .setSrc("http://vaadin.github.io/gwt-polymer-elements/demo/img/vaadin.png")
                        .setMini(true).withClassName("iconvaadin")
                        .setElevation("1").setAttribute("onclick",
                                "javascript:window.open('https://vaadin.com','_blank');")),
                sidebarListPanel);

        return header;
    }

    protected Component initMainView() {
        mainHeaderLabel = new Label();
        mainHeaderLabel.addStyleName("current");

        helpButton = new PaperButton().withClassName("buttons")
                .with(new IronIcon().setIconPolymer("help"))
                .withClickListener(event -> onAboutClick())
                .appendTextContent("ABOUT");

        CssLayout buttonsLayout = new CssLayout();
        buttonsLayout.addStyleName("source-buttons bottom flex");
        javaButton = new PaperButton().withClassName("buttons")
                .setTextContent(".JAVA")
                .with(new IronIcon().setIconPolymer("launch"));
        buttonsLayout.getElement().setTextContent("VIEW SOURCE: ");
        buttonsLayout.addComponents(javaButton);

        contentLayout = new CssLayout();
        contentLayout.addStyleName("panel fit");

        PaperHeaderPanel headerPanel = new PaperHeaderPanel()
                .setMode("waterfall-tall").with(

                        new PaperToolbar().withClassName("toolbar tall animate")
                                .with(new PaperIconButton()
                                        .setIconPolymer("menu")
                                        .setBooleanAttribute(
                                                "paper-drawer-toggle", true),
                                        mainHeaderLabel,
                                        createFlexElement(null), helpButton,
                                        buttonsLayout),
                        contentLayout);
        return headerPanel;
    }

    private Component createAboutDialog() {
        CssLayout buttons = new CssLayout();
        buttons.addStyleName("buttons");
        buttons.addComponent(new PaperButton()
                .setBooleanAttribute("dialog-dismiss", true).setRaised(true)
                .setTextContent("OK")
                // need to update the open property, or next open won't work
                .withClickListener(event -> aboutDialog.setOpened(false)));

        aboutDialog = new PaperDialog().setModal(true)
                .with(new PaperDialogScrollable()
                        .with(new HTML("<h2>PLACEHOLDER</h2>"), buttons));
        return aboutDialog;
    }

    private CssLayout createHorizontalFlexLaoyut(Component... components) {
        CssLayout layout = new CssLayout();
        layout.addStyleName("layout horizontal center center-justified");
        layout.addComponents(components);
        return layout;
    }

    private Component createFlexElement(String innerText) {
        HTML l = new HTML("<span class=\"flex\">"
                + (innerText == null ? "" : innerText) + "</span>");
        return l;
    }

    private Component createSpanElement(String innerText) {
        HTML l = new HTML(
                "<span>" + (innerText == null ? "" : innerText) + "</span>");
        return l;
    }

    private void onAboutClick() {
        aboutDialog.setOpened(true);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = PolymerElementsUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
