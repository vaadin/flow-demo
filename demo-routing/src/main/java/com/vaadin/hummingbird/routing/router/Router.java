package com.vaadin.hummingbird.routing.router;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.vaadin.hummingbird.routing.router.History.PopStateEvent;
import com.vaadin.hummingbird.routing.router.RouteMap.ClassViewMapping;
import com.vaadin.hummingbird.routing.router.RouteMap.DynamicViewMapping;
import com.vaadin.hummingbird.routing.router.RouteMap.ViewMapping;
import com.vaadin.ui.UI;

import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class Router {

    private static final String EVENT_DETAIL_HREF = "event.detail.href";
    private static final String EVENT_DETAIL_INFO = "event.detail.routerlink";

    public enum HistoryUpdateStrategy {
        NO, PUSH, REPLACE
    }

    private final History history;

    private final RouteMap routeMap;

    private ViewDisplay viewDisplay;

    private View errorView;

    private List<ViewMapping> currentViews;

    public Router(UI ui) {
        this(new History(ui));
    }

    public Router(History history) {
        this.history = history;
        routeMap = new RouteMap();

        initRouterLinkClickHandler();

        history.addPopStateListener(this::handlePopState);
    }

    public void open(String path) {
        open(path, HistoryUpdateStrategy.PUSH);
    }

    public void open(final String path, HistoryUpdateStrategy strategy) {
        ArrayDeque<ViewMapping> views = routeMap.findViewsForPath(path);

        openViews(views, strategy, null, path);
    }

    public Router addView(View view) {
        routeMap.registerRoute(view);
        return this;
    }

    public Router addView(Class<? extends View> viewClass) {
        routeMap.registerRoute(viewClass);
        return this;
    }

    public Router addViews(View... view) {
        Stream.of(view).forEach(v -> routeMap.registerRoute(v));
        return this;
    }

    public Router addViews(Class<? extends View>[] viewClasses) {
        Stream.of(viewClasses).forEach(v -> routeMap.registerRoute(v));
        return this;
    }

    public History getHistory() {
        return history;
    }

    public Collection<ViewMapping> getCurrentViews() {
        return Collections.unmodifiableCollection(currentViews);
    }

    public ViewMapping getCurrentTopView() {
        return currentViews == null ? null : currentViews.get(0);
    }

    public ViewDisplay getViewDisplay() {
        return viewDisplay;
    }

    public void setViewDisplay(ViewDisplay viewDisplay) {
        this.viewDisplay = viewDisplay;
    }

    public View getErrorView() {
        return errorView;
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }

    private void initRouterLinkClickHandler() {
        // a nicer way would be:
        // history.getUI().getPage().getJavaScript()
        // .addFunction("vRouterLinkClick", this::onRouterLinkClick);
        UI ui = history.getUI();
        ui.getElement().addEventData("router-link-click", EVENT_DETAIL_HREF);
        ui.getElement().addEventData("router-link-click", EVENT_DETAIL_INFO);
        ui.getElement().addEventListener("router-link-click",
                this::handleRouterLinkClick);
    }

    private void openViews(ArrayDeque<ViewMapping> openedViews,
            HistoryUpdateStrategy strategy, JsonValue state, String path) {
        // handle error case (no view found)
        if (openedViews.isEmpty()) {
            if (errorView != null) {
                // TODO use error view provider or whatnot
                openedViews
                        .add(new DynamicViewMapping(errorView, "error", null));
                strategy = HistoryUpdateStrategy.REPLACE;
            } else {
                throw new IllegalArgumentException(
                        "Trying to open unknown path '" + path
                                + "' and an error view not present");
            }
        }

        int changedViewLevel = 0; // the view level where there are changes
        if (currentViews == null) {
            currentViews = new ArrayList<>();
        } else { // remove removed views
            ArrayDeque<ViewMapping> oldViews = new ArrayDeque<>(currentViews);

            ViewMapping previousMatched = null;
            while (!oldViews.isEmpty()) {
                ViewMapping old = oldViews.pollFirst();
                ViewMapping ny = openedViews.peekFirst();
                if (ny != null && old.getPath().equals(ny.getPath())) {
                    // view is the same
                    openedViews.pollFirst();
                    previousMatched = old;
                    changedViewLevel++;
                } else {
                    // remove first changed view from parent
                    if (previousMatched != null) {
                        previousMatched.getView().remove(old.getView(),
                                old.getPath());
                    }
                    // remove all rest
                    while (old != null) {
                        ViewMapping next = oldViews.pollFirst();
                        if (next != null) {
                            old.getView().remove(next.getView(),
                                    next.getPath());
                        }
                        old.getView().remove();
                        old = next;
                    }
                }
            }

            // remove top level view from view display if changed
            if (viewDisplay != null && changedViewLevel == 0) {
                ViewMapping topLevelView = currentViews.get(0);
                viewDisplay.remove(topLevelView.getView(),
                        topLevelView.getPath());
            }
        }

        // instantiate opened views and swap mapping to instantiated (if
        // necessary)
        ArrayList<ViewMapping> temp = new ArrayList<>(openedViews);
        temp.replaceAll(vm -> {
            if (vm instanceof ClassViewMapping) {
                return new DynamicViewMapping(vm.getView(), vm.getPath(),
                        vm.getParentPath());
            }
            return vm;
        });

        // update current views
        currentViews = new ArrayList<>(
                currentViews.subList(0, changedViewLevel));
        currentViews.addAll(temp);
        openedViews.clear();
        openedViews.addAll(temp);

        updateHistoryState(state, path, strategy);

        // open top level view in display if changed
        if (viewDisplay != null && changedViewLevel == 0) {
            ViewMapping newTopLevelView = currentViews.get(0);
            viewDisplay.show(newTopLevelView.getView(),
                    newTopLevelView.getPath());
        }

        // trigger show sub view in any view that didn't change but sub view
        // changed
        if (changedViewLevel > 0 && !openedViews.isEmpty()) {
            ViewMapping firstNewView = openedViews.peekFirst();
            View view = currentViews.get(changedViewLevel - 1).getView();
            // should open be triggered for all views when sub views has
            // changed?
            view.open(state, path);
            view.show(firstNewView.getView(), firstNewView.getPath());
        } else if (openedViews.isEmpty()) {
            // just sub views were removed, trigger for lowest level view
            currentViews.get(currentViews.size() - 1).getView().open(state,
                    path);
        }

        // trigger open + show in other opened
        while (!openedViews.isEmpty()) {
            ViewMapping vm1 = openedViews.pollFirst();
            vm1.getView().open(state, path);
            if (!openedViews.isEmpty()) {
                ViewMapping vm2 = openedViews.peekFirst();
                vm1.getView().show(vm2.getView(), vm2.getPath());
            }
        }
    }

    private void updateHistoryState(JsonValue state, String path,
            HistoryUpdateStrategy strategy) {
        switch (strategy) {
        case PUSH:
            history.pushState(state, null, path);
            break;
        case REPLACE:
            history.replaceState(state, null, path);
            break;
        case NO:
        default:
            break;
        }
    }

    protected void handlePopState(PopStateEvent event) {
        Logger.getLogger(getClass().getName())
                .info("POPSTATE state: " + event.getState().toJson()
                        + ", host: " + event.getHost() + ", path: "
                        + event.getPath());
        open(event.getPath(), HistoryUpdateStrategy.NO);
    }

    protected void handleRouterLinkClick(JsonObject eventData) {
        String href = null;
        if (eventData.hasKey(EVENT_DETAIL_HREF)) {
            href = eventData.getString(EVENT_DETAIL_HREF);
        }
        JsonValue info = null;
        if (eventData.hasKey(EVENT_DETAIL_INFO)) {
            info = eventData.get(EVENT_DETAIL_INFO);
        }

        Logger.getLogger(getClass().getName())
                .info("ROUTER CLICK, href: " + href + ", info: " + info);
        open(href, HistoryUpdateStrategy.PUSH);
    }

    protected static List<String> splitPathToParts(String path) {
        if (path == null) {
            return Arrays.asList(new String[] { "" });
        }
        if (path.isEmpty() || path.equals("/")) {
            return Arrays.asList(new String[] { "" });
        }

        List<String> split = new ArrayList<String>();
        split.addAll(Arrays.asList(path.split("/")));
        for (Iterator<String> i = split.iterator(); i.hasNext();) {
            if (i.next().isEmpty()) {
                i.remove();
            }
        }
        return split;
    }

    public static String removeFragmentAndParemetersFromPath(String path) {
        if (path.length() > 0) {
            if (path.contains("\\?")) {
                path = path.split("\\?")[0];
            }
            if (path.contains("#")) {
                path = path.split("#")[0];
            }
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path;
    }

}
