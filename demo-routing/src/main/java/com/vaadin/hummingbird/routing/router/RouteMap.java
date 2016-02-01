package com.vaadin.hummingbird.routing.router;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RouteMap {

    public interface ViewMapping {
        View getView();

        String getPath();

        String getParentPath();

    }

    protected static abstract class AbstractViewMapping implements ViewMapping {

        @Override
        public String toString() {
            return getClass().getSimpleName() + ": [" + "getPath()=" + getPath()
                    + ", getParentPath()=" + getParentPath() + "]";
        }
    }

    protected static class DynamicViewMapping extends AbstractViewMapping {

        private final View view;
        private final String path;
        private final String parentPath;

        public DynamicViewMapping(View view, String path, String parentPath) {
            this.view = view;
            this.path = path;
            this.parentPath = parentPath;
        }

        @Override
        public View getView() {
            return view;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public String getParentPath() {
            return parentPath;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((parentPath == null) ? 0 : parentPath.hashCode());
            result = prime * result + ((path == null) ? 0 : path.hashCode());
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
            DynamicViewMapping other = (DynamicViewMapping) obj;
            if (parentPath == null) {
                if (other.parentPath != null) {
                    return false;
                }
            } else if (!parentPath.equals(other.parentPath)) {
                return false;
            }
            if (path == null) {
                if (other.path != null) {
                    return false;
                }
            } else if (!path.equals(other.path)) {
                return false;
            }
            if (view == null) {
                if (other.view != null) {
                    return false;
                }
            } else if (!view.getClass().equals(other.view.getClass())) {
                return false;
            }
            return true;
        }
    }

    protected static class ClassViewMapping extends AbstractViewMapping {

        private final Class<? extends View> viewClass;

        public ClassViewMapping(Class<? extends View> viewClass) {
            this.viewClass = viewClass;
        }

        @Override
        public String getPath() {
            return View.getPath(viewClass);
        }

        @Override
        public String getParentPath() {
            return View.getParentViewPath(viewClass);
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

    protected static class DynamicClassViewMapping extends ClassViewMapping {

        private final String path;
        private final String parentPath;

        public DynamicClassViewMapping(Class<? extends View> viewClass,
                String path, String parentPath) {
            super(viewClass);
            this.path = path;
            this.parentPath = parentPath;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public String getParentPath() {
            return parentPath;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result
                    + ((parentPath == null) ? 0 : parentPath.hashCode());
            result = prime * result + ((path == null) ? 0 : path.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!super.equals(obj)) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            DynamicClassViewMapping other = (DynamicClassViewMapping) obj;
            if (parentPath == null) {
                if (other.parentPath != null) {
                    return false;
                }
            } else if (!parentPath.equals(other.parentPath)) {
                return false;
            }
            if (path == null) {
                if (other.path != null) {
                    return false;
                }
            } else if (!path.equals(other.path)) {
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
        String path = View.getPath(view);
        String parentViewPath = View.getParentViewPath(view);
        return registerRoute(view, path, parentViewPath);
    }

    public RouteMap registerRoute(View view, String path, String parentPath) {
        return registerRoute(new DynamicViewMapping(view, path, parentPath),
                path, parentPath);
    }

    public RouteMap registerRoute(Class<? extends View> view) {
        String path = View.getPath(view);
        String parentViewPath = View.getParentViewPath(view);
        return registerRoute(view, path, parentViewPath);
    }

    public RouteMap registerRoute(Class<? extends View> view, String path,
            String parentPath) {
        return registerRoute(
                new DynamicClassViewMapping(view, path, parentPath), path,
                parentPath);
    }

    public RouteMap registerRoute(ViewMapping viewMapping, String path,
            String parentPath) {
        return registerViewMapping(path, viewMapping)
                .registerSubView(parentPath, path);
    }

    public RouteMap registerViewMapping(String path, ViewMapping viewMapping) {
        Map<String, ViewMapping> map = getViewsMap(true);
        map.put(path, viewMapping);
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
            map.remove(path, new DynamicViewMapping(view, path, parentPath));
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

    protected ArrayDeque<ViewMapping> findViewsForPath(String path) {
        ArrayDeque<ViewMapping> viewTree = new ArrayDeque<>();
        Map<String, ViewMapping> registeredViews = getViewsMap(false);

        if (registeredViews.isEmpty()) {
            return viewTree;
        }
        // match to complete path, e.g. /demo (actually framework/demo)
        ViewMapping viewMapping = registeredViews.get(path);
        if (viewMapping != null) {
            // push parents, if any
            while (viewMapping != null) {
                viewTree.addFirst(viewMapping);
                String parentPath = getParentViewFor(path);
                if (parentPath != null) {
                    for (String s : parentPath.split(" ")) {
                        // just match on first registered parent
                        viewMapping = registeredViews.get(s);
                        if (viewMapping != null) {
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
        } else {
            // match bit by bit, e.g. a view is registered to two parents
            ArrayDeque<String> pathQue = new ArrayDeque<>(
                    Router.splitPathToParts(path));
            StringBuilder viewPathBuilder = new StringBuilder();
            String parentViewPath = null;
            while (!pathQue.isEmpty()) {
                String currentPathPart = pathQue.pop();
                viewPathBuilder.append(currentPathPart);

                String currentPath = viewPathBuilder.toString();
                if (!viewTree.isEmpty()) {
                    // match path to a registered subview or continue to next
                    // path part
                    Set<String> subViews = getSubViewsFor(parentViewPath,
                            false);
                    if (subViews.contains(currentPath)) {
                        viewTree.add(registeredViews.get(currentPath));
                        viewPathBuilder = new StringBuilder();
                        parentViewPath = currentPath;
                    } else {
                        viewPathBuilder.append("/");
                    }
                } else { // searching for top level view
                    ViewMapping view = registeredViews.get(currentPath);
                    if (view != null) {
                        viewTree.add(view);
                        findAndPushParents(currentPath, viewTree);
                        viewPathBuilder = new StringBuilder();
                        parentViewPath = currentPath;
                    } else {
                        viewPathBuilder.append("/");
                    }
                }
            }
        }
        return viewTree;
    }

    private void findAndPushParents(String subViewPath,
            ArrayDeque<ViewMapping> views) {
        String parentPath = getParentViewFor(subViewPath);
        while (parentPath != null) {
            ViewMapping parentView = getViewsMap(false).get(parentPath);
            views.addFirst(parentView);
            parentPath = getParentViewFor(parentPath);
        }
    }
}
