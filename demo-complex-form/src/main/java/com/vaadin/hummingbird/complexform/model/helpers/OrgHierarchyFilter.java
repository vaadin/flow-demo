package com.vaadin.hummingbird.complexform.model.helpers;

import com.vaadin.data.Container.Filter;
import com.vaadin.hummingbird.complexform.model.entity.Goal;
import com.vaadin.hummingbird.complexform.model.entity.Kpi;
import com.vaadin.hummingbird.complexform.model.entity.OrgHierarchy;
import com.vaadin.data.Item;

@SuppressWarnings("serial")
public class OrgHierarchyFilter implements Filter {

    private final long id;
    private final Object propertyId;

    public OrgHierarchyFilter(Object propertyId, long id) {
        this.propertyId = propertyId;
        this.id = id;
    }

    @Override
    public boolean passesFilter(Object itemId, Item item)
            throws UnsupportedOperationException {
        if (itemId instanceof Goal) {
            return ((Goal) itemId).getOrgHierarchy().getId() == id;
        } else if (itemId instanceof Kpi) {
            return ((Kpi) itemId).getGoal().getOrgHierarchy().getId() == id;
        } else if (itemId instanceof OrgHierarchy) {
            return ((OrgHierarchy) itemId).getId() == id;
        }
        return true;
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
        return this.propertyId.equals(propertyId);
    }

}
