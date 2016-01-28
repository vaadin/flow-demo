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
import com.vaadin.ui.UI;

import elemental.json.JsonValue;

public class Router {

    public enum HistoryStateUpdateStrategy {
        NO, PUSH, REPLACE
    }

    private final History history;

    private final RouteMap routeMap;

    private ViewDisplay viewDisplay;

    private View errorView;

    private List<View> currentViews;

    public Router(UI ui) {
        this(new History(ui));
    }

    public Router(History history) {
        this.history = history;
        routeMap = new RouteMap();

        history.addPopStateListener(this::onPopState);
    }

    public void open(String path) {
        open(path, HistoryStateUpdateStrategy.PUSH);
    }

    public void open(final String path,
            HistoryStateUpdateStrategy popStateUpdateStrategy) {
        ArrayDeque<String> pathQue = new ArrayDeque<>(splitPathToParts(path));

        List<View> views = routeMap.findViewsForPath(pathQue);

        openViews(views, popStateUpdateStrategy, null, path);
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

    private void openViews(List<View> viewTree,
            HistoryStateUpdateStrategy strategy, JsonValue state, String path) {
        // handle error case (no view found)
        if (viewTree.isEmpty()) {
            if (errorView != null) {
                viewTree.add(errorView);
                strategy = HistoryStateUpdateStrategy.REPLACE;
            } else {
                throw new IllegalArgumentException(
                        "Trying to open unknown path '" + path
                                + "' and an error view not present");
            }
        }

        int changedViewHierarchyIndex = removeChangedViews(viewTree);

        currentViews = new ArrayList<>(viewTree);

        updateHistoryState(state, path, strategy);

        if (viewDisplay != null && changedViewHierarchyIndex < 1) {
            viewDisplay.show(viewTree.get(0));
        }

        while (!viewTree.isEmpty()) {
            View v1 = viewTree.remove(0);
            v1.open(state, path);
            if (!viewTree.isEmpty()) {
                View v2 = viewTree.get(0);
                if (changedViewHierarchyIndex < 1) {
                    v1.show(v2);
                } else {
                    changedViewHierarchyIndex--;
                }
            }
        }
    }

    private void updateHistoryState(JsonValue state, String path,
            HistoryStateUpdateStrategy strategy) {
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

    private int removeChangedViews(List<View> newViews) {
        int changedViewLevel = 0;
        if (currentViews != null) {
            ArrayDeque<View> oldViews = new ArrayDeque<>(currentViews);
            ArrayDeque<View> copyOfNewViews = new ArrayDeque<>(newViews);
            View previousMatched = null;
            while (!oldViews.isEmpty()) {
                View old = oldViews.pollFirst();
                View ny = copyOfNewViews.pollFirst();
                if (old.getPath().equals(ny.getPath())) {
                    changedViewLevel++;
                    previousMatched = old;
                } else {
                    copyOfNewViews.clear();
                    if (previousMatched != null) {
                        previousMatched.remove(old);
                        previousMatched = null;
                    }
                    old.remove();
                }
            }

            if (viewDisplay != null && changedViewLevel == 0) {
                viewDisplay.remove(currentViews.get(0));
            }
        }
        return changedViewLevel;
    }

    public History getHistory() {
        return history;
    }

    public Collection<View> getCurrentViews() {
        return Collections.unmodifiableCollection(currentViews);
    }

    public View getCurrentTopView() {
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

    private void onPopState(PopStateEvent event) {
        Logger.getLogger(getClass().getName())
                .info("POPSTATE state: " + event.getState().toJson()
                        + ", host: " + event.getHost() + ", path: "
                        + event.getPath());
        open(event.getPath(), HistoryStateUpdateStrategy.NO);
    }

    private static List<String> splitPathToParts(String path) {
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
        }
        return path;
    }

}
