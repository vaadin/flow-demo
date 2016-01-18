package com.vaadin.hummingbird.complexform.ui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.annotations.Bower;
import com.vaadin.annotations.HTML;
import com.vaadin.annotations.TemplateEventHandler;
import com.vaadin.hummingbird.complexform.model.entity.Goal;
import com.vaadin.hummingbird.complexform.model.entity.Kpi;
import com.vaadin.hummingbird.complexform.model.enums.Frequency;
import com.vaadin.hummingbird.complexform.model.enums.UnitType;
import com.vaadin.ui.Template;

import elemental.json.JsonObject;

@HTML({ "context://bower_components/paper-input/paper-textarea.html",
        "context://bower_components/paper-styles/color.html" })
@Bower({ "iron-form", "paper-dropdown-menu", "paper-menu", "paper-item",
        "paper-input", "paper-radio-group", "paper-radio-button",
        "paper-checkbox" })
public class KpiForm extends Template {

    public interface KpiGoal {
        public String getName();

        public void setName(String goalName);

        public long getIndex();

        public void setIndex(long index);

        public KpiFrequency getFrequency();

        public void setFrequency(KpiFrequency frequency);
    }

    public interface KpiFrequency {
        public String getName();

        public void setName(String name);
    }

    public interface KpiUnitType {
        public String getName();

        public void setName(String name);
    }

    public interface KpiModel extends Model {

        public List<KpiGoal> getGoalOptions();

        public void setGoalOptions(List<KpiGoal> goalOptions);

        public List<KpiFrequency> getFrequencyOptions();

        public void setFrequencyOptions(List<KpiFrequency> frequencyOptions);

        public List<KpiUnitType> getUnitTypes();

        public void setUnitTypes(List<KpiUnitType> unitTypes);

        public KpiUnitType getUnitType();

        public void setUnitType(KpiUnitType unitType);

        public KpiGoal getGoal();

        public void setGoal(KpiGoal goal);

        public String getName();

        public void setName(String name);

        public String getDescription();

        public void setDescription(String description);

        public String getHeader();

        public void setHeader(String object);

        public String getDataSource();

        public void setDataSource(String dataSource);

        public boolean isActive();

        public void setActive(boolean active);
    }

    public KpiForm() {
        getModel().setFrequencyOptions(Stream.of(Frequency.values()).map(f -> {
            KpiFrequency kf = Model.create(KpiFrequency.class);
            kf.setName(f.toCaption());
            return kf;
        }).collect(Collectors.toList()));

        getModel().setUnitTypes(Stream.of(UnitType.values()).map(ut -> {
            KpiUnitType kut = Model.create(KpiUnitType.class);
            kut.setName(ut.toCaption());
            return kut;
        }).collect(Collectors.toList()));
    }

    @Override
    public KpiModel getModel() {
        return (KpiModel) super.getModel();
    }

    @TemplateEventHandler
    public void onCancel() {
        // TODO
    }

    @TemplateEventHandler
    public void onCommit(JsonObject object) {
        // TODO
    }

    public void setKpi(Kpi kpi) {
        KpiModel model = getModel();
        model.setHeader(kpi.getId() == 0 ? "Create new KPI" : "Edit KPI");

        if (kpi.getGoal() != null) {
            model.setGoal(convertGoal(kpi.getGoal()));
        }
        model.setName(kpi.getName());
        model.setDescription(kpi.getDescription());
        model.setDataSource(kpi.getDataSource());
        model.setUnitType(convertUnitType(kpi.getUnitType()));
    }

    public void setGoalOptions(List<Goal> goalOptions) {
        KpiModel model = getModel();
        for (Goal goal : goalOptions) {
            model.getGoalOptions().add(convertGoal(goal));
        }
    }

    private KpiUnitType convertUnitType(String unitType) {
        KpiUnitType kut = Model.create(KpiUnitType.class);
        kut.setName(UnitType.valueOf(unitType).toCaption());
        return kut;
    }

    private KpiFrequency convertFrequency(Frequency f) {
        return convertFrequency(f.toCaption());
    }

    private KpiFrequency convertFrequency(String f) {
        KpiFrequency kf = Model.create(KpiFrequency.class);
        kf.setName(f);
        return kf;
    }

    private KpiGoal convertGoal(Goal goal) {
        KpiGoal kpiGoal = Model.create(KpiGoal.class);
        kpiGoal.setName(goal.getName() + " - "
                + goal.getOrgHierarchy().getEntityOwnerFname() + " "
                + goal.getOrgHierarchy().getEntityOwnerLname());
        kpiGoal.setIndex(goal.getGoalId());
        kpiGoal.setFrequency(
                convertFrequency(Frequency.valueOf(goal.getFrequency())));
        return kpiGoal;
    }
}
