package com.vaadin.hummingbird.complexform.model.helpers;

import com.vaadin.data.Container.Filter;
import com.vaadin.hummingbird.complexform.model.entity.Goal;
import com.vaadin.hummingbird.complexform.model.entity.Kpi;
import com.vaadin.data.Item;

@SuppressWarnings("serial")
public class GoalFilter implements Filter {

    private final Object propertyId;
    private final long id;

    public GoalFilter(Object propertyId, long id) {
        this.propertyId = propertyId;
        this.id = id;
    }

    @Override
    public boolean passesFilter(Object itemId, Item item)
            throws UnsupportedOperationException {
        if (itemId instanceof Goal) {
            return id == ((Goal) itemId).getGoalId();
        } else if (itemId instanceof Kpi) {
            return id == ((Kpi) itemId).getGoal().getGoalId();
        }
        return true;
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
        return this.propertyId.equals(propertyId);
    }

}
