package com.vaadin.hummingbird;

import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Template;
import com.vaadin.ui.UI;

@Theme("simplecrm")
@Title("Hummingbird Simple CRM")
public class CrmUI extends UI {
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CrmUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public class MyNavStateManager implements NavigationStateManager {

        private Navigator navigator;
        private SimpleCrmMain mainTemplate;
        private String state = "";

        public MyNavStateManager(SimpleCrmMain main) {
            setMainTemplate(main);
        }

        @Override
        public String getState() {
            return state;
        }

        @Override
        public void setState(String state) {
            mainTemplate.getElement().getNode()
                    .enqueueRpc("history.pushState({}, '', $0)", state);
            this.state = state;
        }

        @Override
        public void setNavigator(Navigator navigator) {
            this.navigator = navigator;
        }

        public void setMainTemplate(SimpleCrmMain main) {
            mainTemplate = main;
        }
    }

    public interface MyView extends View {
        public Template getTemplate();
    }

    public class MyViewDisplay implements ViewDisplay {

        private SimpleCrmMain mainTemplate;

        public MyViewDisplay(SimpleCrmMain main) {
            mainTemplate = main;
        }

        @Override
        public void showView(View view) {
            if (view instanceof MyView) {
                mainTemplate.showTemplate(((MyView) view).getTemplate());
            } else {
                throw new IllegalStateException("Unknown view type");
            }
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        CustomerData customerData = new CustomerData();
        String pathInfo = request.getPathInfo();
        SimpleCrmMain main = new SimpleCrmMain(customerData);
        Navigator navigator = new Navigator(this, new MyNavStateManager(main),
                new MyViewDisplay(main));
        navigator.addView("Customers", Customers.class);
        navigator.addView("About", About.class);
        navigator.addView("Analyze", Analyze.class);
        navigator.addView("Map", Map.class);
        navigator.setErrorView(Customers.class);
        setNavigator(navigator);
        if (pathInfo != null) {
            pathInfo = pathInfo.replaceAll("/", "");
        } else {
            pathInfo = "";
        }
        if (!"".equals(pathInfo)) {
            getUI().getNavigator().navigateTo(pathInfo);
        } else {
            navigator.navigateTo("Customers");
        }
        setContent(main);
    }

}