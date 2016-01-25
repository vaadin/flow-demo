package com.vaadin.hummingbird.routing.router;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vaadin.hummingbird.routing.router.History.PopStateEvent;
import com.vaadin.ui.UI;

public class Router {

    private final History history;

    private Map<String, View> viewsMap;

    private Map<String, Set<String>> subViewsMap;

    public Router(UI ui) {
        this(new History(ui));
    }

    public Router(History history) {
        this.history = history;

        history.addPopStateListener(this::onPopState);
    }

    private void onPopState(PopStateEvent event) {
        System.out.println("POPSTATE: " + event.getState().toJson());
    }

    public Router addView(View view) {
        return addView(view, view.url(), view.aliases(), view.parentViewUrls());
    }

    public Router addView(View view, String url, String aliases,
            String parentViewUrl) {
        Map<String, View> map = getViewsMap(true);
        map.put(url, view);
        if (aliases != null) {
            for (String alias : aliases.split(" ")) {
                map.put(alias, view);
            }
        }
        if (view.parentViewUrls() != null) {
            for (String parent : parentViewUrl.split(" ")) {
                getSubViewsFor(parent, true).add(view.url());
            }
        }
        return this;
    }

    public Router removeView(View view) {
        return removeView(view, view.url(), view.aliases(),
                view.parentViewUrls());
    }

    public Router removeView(View view, String url, String aliases,
            String parentViewUrl) {
        // remove view
        Map<String, View> map = getViewsMap(false);
        if (map != null) {
            if (view != null) {
                map.remove(url, view);
            } else {
                map.remove(url);
            }
            // remove aliases
            if (aliases != null) {
                for (String alias : aliases.split(" ")) {
                    if (view != null) {
                        map.remove(alias, view);
                    } else {
                        map.remove(alias);
                    }
                }
            }
        }
        // remove from parent view
        if (parentViewUrl != null) {
            for (String parent : parentViewUrl.split(" ")) {
                Set<String> subViews = getSubViewsFor(parent, false);
                if (subViews != null) {
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

    private Map<String, View> getViewsMap(boolean createIfNeeded) {
        if (createIfNeeded && viewsMap == null) {
            viewsMap = new HashMap<>();
        }
        return viewsMap;
    }

    private Map<String, Set<String>> getSubViewsMap(boolean createIfNeeded) {
        if (createIfNeeded && subViewsMap == null) {
            subViewsMap = new HashMap<>();
        }
        return subViewsMap;
    }

    private Set<String> getSubViewsFor(String parentView,
            boolean createIfNeeded) {
        Map<String, Set<String>> subViewsMap = getSubViewsMap(createIfNeeded);
        if (createIfNeeded) {
            Set<String> subViews = subViewsMap.get(parentView);
            if (subViews == null) {
                subViews = new HashSet<>();
                subViewsMap.put(parentView, subViews);
            }
            return subViews;
        } else {
            return subViewsMap == null ? null : subViewsMap.get(parentView);
        }
    }
}
