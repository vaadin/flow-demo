package com.vaadin.hummingbird.routing.router;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vaadin.hummingbird.routing.router.History.PopStateEvent;
import com.vaadin.ui.UI;

import elemental.json.JsonValue;

public class Router {

    public enum HistoryStateUpdateStrategy {
        NO, PUSH, REPLACE
    }

    private final History history;

    private Map<String, View> viewsMap;

    private Map<String, String> aliases;

    private Map<String, Set<String>> subViewsMap;

    private ArrayDeque<View> currentViews;

    private ViewDisplay viewDisplay;

    private View errorView;

    public Router(UI ui) {
        this(new History(ui));
    }

    public Router(History history) {
        this.history = history;

        history.addPopStateListener(this::onPopState);
    }

    public void open(String path) {
        open(path, HistoryStateUpdateStrategy.PUSH);
    }

    public void open(final String path,
            HistoryStateUpdateStrategy popStateUpdateStrategy) {
        ArrayDeque<String> pathQue = new ArrayDeque<>(splitPathToParts(path));

        String pathWithoutParams = removeFragmentAndParemetersFromPath(path);

        Map<String, View> registeredViews = getViewsMap(false);
        ArrayDeque<View> views = new ArrayDeque<>();
        // first check for matching aliases
        if (getAliasesMap(false).containsKey(pathWithoutParams)) {
            String realPath = getAliasesMap(false).get(pathWithoutParams);
            View aliasView = registeredViews.get(realPath);
            views.add(aliasView);
            String parentUrl = aliasView.getParentViewPath();
            while (parentUrl != null) {
                View parentView = registeredViews.get(parentUrl);
                views.addFirst(parentView);
                parentUrl = parentView.getParentViewPath();
            }
        } else if (!pathQue.isEmpty() && registeredViews != null) {
            // match path to views from start of path
            StringBuilder viewPathBuilder = new StringBuilder();
            while (!pathQue.isEmpty()) {
                String currentPathPart = pathQue.pop();
                // last might contain parameters and fragment
                if (pathQue.size() == 0 && currentPathPart.length() > 0) {
                    currentPathPart = removeFragmentAndParemetersFromPath(
                            currentPathPart);
                }
                viewPathBuilder.append(currentPathPart);

                if (!views.isEmpty()) {
                    View parentView = views.peekLast();
                    if (parentView != null) {
                        // match path to a registered subview or continue
                        Set<String> subViews = getSubViewsFor(
                                parentView.getPath(), false);
                        if (subViews != null && subViews
                                .contains(viewPathBuilder.toString())) {
                            views.add(registeredViews
                                    .get(viewPathBuilder.toString()));
                            viewPathBuilder = new StringBuilder();
                        } else {
                            viewPathBuilder.append("/");
                        }
                    }
                } else { // searching for top level view
                    View view = registeredViews.get(viewPathBuilder.toString());
                    if (view != null) {
                        views.add(view);
                        viewPathBuilder = new StringBuilder();
                    } else {
                        viewPathBuilder.append("/");
                    }
                }
            }
        }

        openViews(views, popStateUpdateStrategy, null, path);
    }

    private void openViews(ArrayDeque<View> viewTree,
            HistoryStateUpdateStrategy strategy, JsonValue state, String path) {
        // handle error case (no view found)
        if (viewTree.isEmpty()) {
            if (errorView != null) {
                viewTree.add(errorView);
                strategy = HistoryStateUpdateStrategy.REPLACE;
                path = errorView.getPath();
            } else {
                throw new IllegalArgumentException(
                        "Trying to open unknown path '" + path
                                + "' and an error view not present");
            }
        }

        int changedViewHierarchyIndex = removeChangedViews(viewTree);

        currentViews = new ArrayDeque<>(viewTree);

        updateHistoryState(state, path, strategy);

        if (viewDisplay != null && changedViewHierarchyIndex < 1) {
            viewDisplay.show(this, viewTree.peekFirst());
        }

        while (!viewTree.isEmpty()) {
            View v1 = viewTree.pollFirst();
            v1.open(state, path);
            View v2 = viewTree.peekFirst();
            if (v2 != null && changedViewHierarchyIndex < 1) {
                v1.show(v2);
            } else {
                changedViewHierarchyIndex--;
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

    private int removeChangedViews(ArrayDeque<View> newViews) {
        int changedViewLevel = 0;
        if (currentViews != null) {
            ArrayDeque<View> oldViews = new ArrayDeque<>(currentViews);
            ArrayDeque<View> copyOfNewViews = new ArrayDeque<>(newViews);
            View previousMatched = null;
            while (!oldViews.isEmpty()) {
                View old = oldViews.pollFirst();
                View ny = copyOfNewViews.pollFirst();
                if (old.equals(ny)) {
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
                viewDisplay.remove(currentViews.peekFirst());
            }
        }
        return changedViewLevel;
    }

    private void openErrorView(String path) {
        if (errorView != null) {
            getHistory().replaceState(null, "error", errorView.getPath());
            viewDisplay.show(this, errorView);
            errorView.open(null, path);
            currentViews = new ArrayDeque<>();
            currentViews.add(errorView);
        } else {
            throw new IllegalArgumentException("Trying to open unknown path '"
                    + path + "' and an error view not present");
        }
    }

    public History getHistory() {
        return history;
    }

    public Collection<View> getCurrentViews() {
        return Collections.unmodifiableCollection(currentViews);
    }

    public View getCurrentTopView() {
        return currentViews == null ? null : currentViews.peekFirst();
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

    public List<View> getRegisteredViews() {
        return Collections.unmodifiableList(new ArrayList<>(viewsMap.values()));
    }

    public List<View> getRegisteredSubViews(View view) {
        Set<String> subViewUrls = getSubViewsFor(view.getPath(), false);
        ArrayList<View> subViews = new ArrayList<>();
        if (subViewUrls != null) {
            subViewUrls.forEach(s -> subViews.add(viewsMap.get(s)));
        }
        return Collections.unmodifiableList(subViews);
    }

    public Router addView(View view) {
        return addView(view, view.getPath(), view.getAliases(),
                view.getParentViewPath());
    }

    public Router addView(View view, String url, String aliases,
            String parentViewUrl) {
        Map<String, View> map = getViewsMap(true);
        map.put(url, view);
        if (aliases != null) {
            Map<String, String> aliasesMap = getAliasesMap(true);
            for (String alias : aliases.split(" ")) {
                aliasesMap.put(alias, url);
            }
        }
        if (view.getParentViewPath() != null) {
            for (String parent : parentViewUrl.split(" ")) {
                getSubViewsFor(parent, true).add(view.getPath());
            }
        }
        return this;
    }

    public Router removeView(View view) {
        return removeView(view, view.getPath(), view.getAliases(),
                view.getParentViewPath());
    }

    public Router removeView(View view, String url, String aliases,
            String parentViewUrl) {
        // remove view
        Map<String, View> map = getViewsMap(false);
        if (view != null) {
            map.remove(url, view);
        } else {
            map.remove(url);
        }
        // remove aliases
        if (aliases != null) {
            Map<String, String> alisesMap = getAliasesMap(false);
            for (String alias : aliases.split(" ")) {
                if (view != null) {
                    alisesMap.remove(alias, url);
                } else {
                    alisesMap.remove(alias);
                }
            }
        }
        // remove from parent view
        if (parentViewUrl != null) {
            for (String parent : parentViewUrl.split(" ")) {
                Set<String> subViews = getSubViewsFor(parent, false);
                if (!subViews.isEmpty()) {
                    subViews.remove(url);
                    if (subViews.isEmpty()) {
                        subViewsMap.remove(parent);
                        if (subViewsMap.isEmpty()) {
                            subViewsMap = null;
                            break;
                        }
                    }
                }
            }
        }
        return this;

    }

    private void onPopState(PopStateEvent event) {
        // TODO
        System.out.println("POPSTATE state: " + event.getState().toJson()
                + ", host: " + event.getHost() + ", path: " + event.getPath());
    }

    private Map<String, View> getViewsMap(boolean createIfNeeded) {
        if (viewsMap == null) {
            if (createIfNeeded) {
                viewsMap = new LinkedHashMap<>();
            } else {
                return Collections.emptyMap();
            }
        }
        return viewsMap;
    }

    private Map<String, String> getAliasesMap(boolean createIfNeeded) {
        if (aliases == null) {
            if (createIfNeeded) {
                aliases = new LinkedHashMap<>();
            } else {
                return Collections.emptyMap();
            }
        }
        return aliases;
    }

    private Map<String, Set<String>> getSubViewsMap(boolean createIfNeeded) {
        if (subViewsMap == null) {
            if (createIfNeeded) {
                subViewsMap = new LinkedHashMap<>();
            } else {
                return Collections.emptyMap();
            }
        }
        return subViewsMap;
    }

    private Set<String> getSubViewsFor(String parentView,
            boolean createIfNeeded) {
        Map<String, Set<String>> subViewsMap = getSubViewsMap(createIfNeeded);
        Set<String> subViews = subViewsMap.get(parentView);
        if (subViews == null) {
            if (createIfNeeded) {
                subViews = new HashSet<>();
                subViewsMap.put(parentView, subViews);
                return subViews;
            } else {
                return Collections.emptySet();
            }
        } else {
            return subViews;
        }
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
