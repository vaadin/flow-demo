package com.vaadin.hummingbird.demo;

import java.util.Arrays;
import java.util.stream.Stream;

import com.vaadin.annotations.PolymerStyle;
import com.vaadin.hummingbird.demo.integration.CustomerFormDemo;
import com.vaadin.hummingbird.demo.paper.ButtonSample;
import com.vaadin.hummingbird.demo.paper.CheckboxSample;
import com.vaadin.hummingbird.demo.paper.DialogSample;
import com.vaadin.hummingbird.demo.paper.DropdownMenuSample;
import com.vaadin.hummingbird.demo.paper.FabSample;
import com.vaadin.hummingbird.demo.paper.HeaderPanelSample;
import com.vaadin.hummingbird.demo.paper.IconButtonSample;
import com.vaadin.hummingbird.demo.paper.InputSample;
import com.vaadin.hummingbird.demo.paper.ItemSample;
import com.vaadin.hummingbird.demo.paper.MaterialSample;
import com.vaadin.hummingbird.demo.paper.MenuSample;
import com.vaadin.hummingbird.demo.paper.ProgressSample;
import com.vaadin.hummingbird.demo.paper.RadioButtonSample;
import com.vaadin.hummingbird.demo.paper.RadioGroupSample;
import com.vaadin.hummingbird.demo.vaadin.ComboBoxSample;
import com.vaadin.hummingbird.iron.IronCollapse;
import com.vaadin.hummingbird.iron.IronFlexLayout;
import com.vaadin.hummingbird.iron.IronIcon;
import com.vaadin.hummingbird.iron.IronIcons;
import com.vaadin.hummingbird.iron.IronSelector;
import com.vaadin.hummingbird.iron.event.IronSelectEvent;
import com.vaadin.hummingbird.paper.PaperButton;
import com.vaadin.hummingbird.paper.PaperDialog;
import com.vaadin.hummingbird.paper.PaperDialogScrollable;
import com.vaadin.hummingbird.paper.PaperDrawerPanel;
import com.vaadin.hummingbird.paper.PaperDropdownMenu;
import com.vaadin.hummingbird.paper.PaperFab;
import com.vaadin.hummingbird.paper.PaperHeaderPanel;
import com.vaadin.hummingbird.paper.PaperIconButton;
import com.vaadin.hummingbird.paper.PaperItem;
import com.vaadin.hummingbird.paper.PaperMenu;
import com.vaadin.hummingbird.paper.PaperRipple;
import com.vaadin.hummingbird.paper.PaperStyles;
import com.vaadin.hummingbird.paper.PaperToolbar;
import com.vaadin.hummingbird.polymer.PolymerComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;
import com.vaadin.ui.Label;

@PolymerStyle("main-styles")
public class PolymerSampler extends CssLayout {

    private CssLayout sidebarListPanel;
    private Label mainHeaderLabel;
    private PaperButton helpButton;
    private PaperButton javaButton;
    private CssLayout contentLayout;
    private PaperDialog aboutDialog;
    private PaperMenu styleSelector;
    private IronCollapse currentCollapse;

    public PolymerSampler() {
        CssLayout drawer = new CssLayout();
        drawer.getElement().setAttribute("attr.drawer", true);
        drawer.addComponent(initHeaderSidebar());

        CssLayout main = new CssLayout();
        main.getElement().setAttribute("attr.main", true);
        main.addComponent(initMainHeader());

        final PaperDrawerPanel paperDrawerPanel = new PaperDrawerPanel()
                .setDrawerWidth("250px").with(drawer, main);

        addComponents(createPolymerElementDependantComponents());
        addComponents(paperDrawerPanel);
        // , createAboutDialog());

        addSamples();
    }

    public void setCurrentTheme(String currentTheme) {
        styleSelector.setSelected(Integer.toString(
                Arrays.asList(PolymerThemes.themes).indexOf(currentTheme)));
    }

    private void addSamples() {
        final IronSelector paperCollapse = addCategory("paper",
                "Paper Elements");
        paperCollapse.with(addSample("Button", false),
                addSample("CheckBox", false), addSample("Dialog", false),
                addSample("DropdownMenu", false),
                addSample("Floating Button", false),
                addSample("Header Panel", false),
                addSample("Icon Button", false), addSample("Input", false),
                addSample("Item", false), addSample("Material", false),
                addSample("Menu", false), addSample("Progress", false),
                addSample("Radio Button", false),
                addSample("Radio Group", false), addSample("Ripple"),
                addSample("Spinner"), addSample("Slider"), addSample("Tabs"),
                addSample("Toast"), addSample("Toggle Button"),
                addSample("Toolbar"));

        addCategory("iron", "Iron Elements").with(addSample("Collapse"),
                addSample("Image"), addSample("List"), addSample("Selector"));

        addCategory("vaadin", "Vaadin Elements")
                .with(addSample("ComboBox", false));
        addCategory("hummingbird", "Hummingbird Integration")
                .with(addSample("Customer Form Demo", false));

    }

    private IronSelector addCategory(String path, String name) {
        final IronCollapse collapse = new IronCollapse();

        PaperItem item = new PaperItem()
                .withClassName("category "
                        + name.toLowerCase().replace(" ", "-") + "-category")
                .with(new IronIcon().setIconPolymer("expand-more"),
                        createSpanElement(name), new PaperRipple())
                .withClickListener(event -> {
                    collapse.setOpened(!collapse.isOpened());
                    if (currentCollapse != null) {
                        currentCollapse.setOpened(false);
                    }
                    currentCollapse = collapse;
                });

        IronSelector selector = new IronSelector();
        collapse.with(selector);

        sidebarListPanel.addComponents(item, collapse);

        return selector;
    }

    private PaperItem addSample(String name) {
        return addSample(name, true);
    }

    private PaperItem addSample(String name, boolean disabled) {
        return new PaperItem().setDisabled(disabled)
                .with(createSpanElement(name))
                .withClassName("item " + name.toLowerCase() + "-sample")
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
        case "DropdownMenu":
            return new DropdownMenuSample();
        case "Floating Button":
            return new FabSample();
        case "Header Panel":
            return new HeaderPanelSample();
        case "Icon Button":
            return new IconButtonSample();
        case "Item":
            return new ItemSample();
        case "Input":
            return new InputSample();
        case "Material":
            return new MaterialSample();
        case "Menu":
            return new MenuSample();
        case "Progress":
            return new ProgressSample();
        case "Radio Button":
            return new RadioButtonSample();
        case "Radio Group":
            return new RadioGroupSample();
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
        case "ComboBox":
            return new ComboBoxSample();
        case "Customer Form Demo":
            return new CustomerFormDemo();
        default:
            return new Label("Sample " + sampleName + " not yet implemented");
        }
    }

    private PolymerComponent<?>[] createPolymerElementDependantComponents() {
        return new PolymerComponent[] { new PaperStyles(), new IronIcons(),
                new IronFlexLayout() };
    }

    private Component initHeaderSidebar() {
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

    protected Component initMainHeader() {
        mainHeaderLabel = new Label();
        mainHeaderLabel.addStyleName("current");

        helpButton = new PaperButton().withClassName("buttons")
                .with(new IronIcon().setIconPolymer("help"))
                .withClickListener(event -> onAboutClick())
                .appendTextContent("ABOUT");

        PaperDropdownMenu themeDropDown = new PaperDropdownMenu()
                .setLabel("Theme").withClassName("theme-select")
                .with(styleSelector = new PaperMenu()
                        .withClassName("dropdown-content")
                        .addEventData(IronSelectEvent.class,
                                "event.target.selected")
                        .with(createThemeItems()));

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
                                        createFlexElement(null), themeDropDown,
                                        helpButton, buttonsLayout),
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

    private Component[] createThemeItems() {
        return Stream.of(PolymerThemes.themes).map(str -> {
            return new PaperItem().setTextContent(str).setAttribute("onclick",
                    "javascript:window.open('?theme=" + str + "','_self');");
        }).toArray(PaperItem[]::new);
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

}
