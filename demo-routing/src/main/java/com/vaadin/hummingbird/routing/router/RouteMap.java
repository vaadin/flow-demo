package com.vaadin.hummingbird.routing.router;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RouteMap {

    private interface ViewMapping {
        public View getView();
    }

    private static class DirectViewMapping implements ViewMapping {

        private final View view;

        public DirectViewMapping(View view) {
            this.view = view;
        }

        @Override
        public View getView() {
            return view;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((view == null) ? 0 : view.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            DirectViewMapping other = (DirectViewMapping) obj;
            if (view == null) {
                if (other.view != null) {
                    return false;
                }
            } else if (!view.equals(other.view)) {
                return false;
            }
            return true;
        }
    }

    private static class ClassViewMapping implements ViewMapping {

        private final Class<? extends View> viewClass;

        public ClassViewMapping(Class<? extends View> viewClass) {
            this.viewClass = viewClass;
        }

        @Override
        public View getView() {
            try {
                View view = viewClass.newInstance();
                return view;
            } catch (InstantiationException e) {
                // TODO error handling
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                // TODO error handling
                throw new RuntimeException(e);
            }
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((viewClass == null) ? 0 : viewClass.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ClassViewMapping other = (ClassViewMapping) obj;
            if (viewClass == null) {
                if (other.viewClass != null) {
                    return false;
                }
            } else if (!viewClass.equals(other.viewClass)) {
                return false;
            }
            return true;
        }
    }

    private Map<String, ViewMapping> viewsMap;

    private Map<String, Set<String>> subViewsMap;

    protected RouteMap() {

    }

    public RouteMap registerRoute(View view) {
        String path = view.getPath();
        String parentViewPath = view.getParentViewPath();
        return registerRoute(view, path, parentViewPath);
    }

    public RouteMap registerRoute(View view, String path) {
        return registerRoute(view, path, null);
    }

    public RouteMap registerRoute(View view, String path, String parentPath) {
        Map<String, ViewMapping> map = getViewsMap(true);
        map.put(path, new DirectViewMapping(view));
        registerSubView(parentPath, path);
        return this;
    }

    public RouteMap registerRoute(Class<? extends View> view) {
        String path = View.getPath(view);
        String parentViewPath = View.getParentViewPath(view);
        return registerRoute(view, path, parentViewPath);
    }

    public RouteMap registerRoute(Class<? extends View> view, String path) {
        return registerRoute(view, path, null);
    }

    public RouteMap registerRoute(Class<? extends View> view, String path,
            String parentPath) {
        Map<String, ViewMapping> map = getViewsMap(true);
        map.put(path, new ClassViewMapping(view));
        registerSubView(parentPath, path);
        return this;
    }

    public RouteMap registerSubView(String parentViewPath, String subViewPath) {
        if (parentViewPath != null && !parentViewPath.isEmpty()) {
            getSubViewsFor(parentViewPath, true).add(subViewPath);
        }
        return this;
    }

    public RouteMap unregisterRoute(View view) {
        String path = View.getPath(view);
        String parentViewPath = View.getParentViewPath(view);
        return unregisterRoute(view, null, path, parentViewPath);
    }

    public RouteMap unregisterRoute(Class<? extends View> viewClass) {
        String path = View.getPath(viewClass);
        String parentViewPath = View.getParentViewPath(viewClass);
        return unregisterRoute(null, viewClass, path, parentViewPath);
    }

    private RouteMap unregisterRoute(View view, Class<? extends View> viewClass,
            String path, String parentPath) {
        Map<String, ViewMapping> map = getViewsMap(false);
        if (view == null && viewClass == null) {
            map.remove(path);
        } else if (viewClass != null) {
            map.remove(path, new ClassViewMapping(viewClass));
        } else {
            map.remove(path, new DirectViewMapping(view));
        }
        unregisterSubView(parentPath, path);
        return this;
    }

    public RouteMap unregisterSubView(String parentViewPath,
            String subViewPath) {
        if (parentViewPath != null && !parentViewPath.isEmpty()) {
            Set<String> subViews = getSubViewsFor(parentViewPath, false);
            if (!subViews.isEmpty()) {
                subViews.remove(subViewPath);
                if (subViews.isEmpty()) {
                    subViewsMap.remove(parentViewPath);
                    if (subViewsMap.isEmpty()) {
                        subViewsMap = null;
                    }
                }
            }
        }
        return this;
    }

    protected Map<String, ViewMapping> getViewsMap(boolean createIfNeeded) {
        if (viewsMap == null) {
            if (createIfNeeded) {
                viewsMap = new LinkedHashMap<>();
            } else {
                return Collections.emptyMap();
            }
        }
        return viewsMap;
    }

    protected Map<String, Set<String>> getSubViewsMap(boolean createIfNeeded) {
        if (subViewsMap == null) {
            if (createIfNeeded) {
                subViewsMap = new LinkedHashMap<>();
            } else {
                return Collections.emptyMap();
            }
        }
        return subViewsMap;
    }

    protected Set<String> getSubViewsFor(String parentView,
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

    protected String getParentViewFor(String subViewPath) {
        for (Entry<String, Set<String>> entry : getSubViewsMap(false)
                .entrySet()) {
            if (entry.getValue().contains(subViewPath)) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected List<View> findViewsForPath(ArrayDeque<String> pathQue) {
        Map<String, ViewMapping> registeredViews = getViewsMap(false);
        List<View> views = new ArrayList<>();
        if (!pathQue.isEmpty() && !registeredViews.isEmpty()) {
            // match path to views from start of path
            StringBuilder viewPathBuilder = new StringBuilder();
            String parentViewPath = null;
            while (!pathQue.isEmpty()) {
                String currentPathPart = pathQue.pop();
                // last might contain parameters and fragment
                if (pathQue.size() == 0 && currentPathPart.length() > 0) {
                    currentPathPart = Router
                            .removeFragmentAndParemetersFromPath(
                                    currentPathPart);
                    // TODO handle case where there was ..foobar/?params...
                }
                viewPathBuilder.append(currentPathPart);

                String currentPath = viewPathBuilder.toString();
                if (!views.isEmpty()) {
                    // match path to a registered subview or continue to next
                    // path part
                    Set<String> subViews = getSubViewsFor(parentViewPath,
                            false);
                    if (subViews.contains(currentPath)) {
                        views.add(registeredViews.get(currentPath).getView());
                        viewPathBuilder = new StringBuilder();
                        parentViewPath = currentPath;
                    } else {
                        viewPathBuilder.append("/");
                    }
                } else { // searching for top level view
                    ViewMapping view = registeredViews.get(currentPath);
                    if (view != null) {
                        views.add(view.getView());
                        findAndPushParents(currentPath, views);
                        viewPathBuilder = new StringBuilder();
                        parentViewPath = currentPath;
                    } else {
                        viewPathBuilder.append("/");
                    }
                }
            }
        }
        return views;
    }

    private void findAndPushParents(String subViewPath, List<View> views) {
        String parentPath = getParentViewFor(subViewPath);
        while (parentPath != null) {
            ViewMapping parentView = getViewsMap(false).get(parentPath);
            views.add(0, parentView.getView());
            parentPath = getParentViewFor(parentPath);
        }
    }
}
