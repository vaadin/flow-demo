package com.vaadin.hummingbird.complexform.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.vaadin.hummingbird.complexform.model.entity.Goal;
import com.vaadin.hummingbird.complexform.model.entity.Kpi;
import com.vaadin.hummingbird.complexform.model.entity.OrgHierarchy;

public class Backend {

    private static Goal goal1 = new Goal() {
        {
            setGoalId(1);
            setName("V8");
            setStartDate(new Date(
                    Instant.parse("2016-01-01T00:00:00.00Z").getEpochSecond()));
            setEndDate(new Date(
                    Instant.parse("2016-12-31T23:59:59.00Z").getEpochSecond()));
        }
    };
    private static Goal goal2 = new Goal() {
        {
            setGoalId(2);
            setName("[Hummingbird] 1.0");
            setStartDate(new Date(
                    Instant.parse("2016-01-01T00:00:00.00Z").getEpochSecond()));
            setEndDate(new Date(
                    Instant.parse("2016-12-31T23:59:59.00Z").getEpochSecond()));
            setFrequency("M");
        }
    };
    private static Goal goal3 = new Goal() {
        {
            setGoalId(3);
            setName("Elements 1.0");
            setStartDate(new Date(
                    Instant.parse("2016-01-01T00:00:00.00Z").getEpochSecond()));
            setEndDate(new Date(
                    Instant.parse("2016-12-31T23:59:59.00Z").getEpochSecond()));
        }
    };
    private static OrgHierarchy vaadin = new OrgHierarchy() {
        {
            setEntityName("Vaadin");
            setEntityOwnerFname("Joonas");
            setEntityOwnerLname("Lehtinen");
            addGoal(goal1);
            addGoal(goal2);
            addGoal(goal3);
        }
    };

    public static Kpi createNewKpi() {
        Kpi kpi = new Kpi();
        return kpi;
    }

    public static List<Goal> getGoalOptions() {
        return Arrays.asList(new Goal[] { goal1, goal2, goal3 });
    }

}
