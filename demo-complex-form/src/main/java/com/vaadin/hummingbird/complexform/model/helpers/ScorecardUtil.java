package com.vaadin.hummingbird.complexform.model.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.vaadin.data.Validator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.hummingbird.complexform.model.entity.Goal;
import com.vaadin.hummingbird.complexform.model.entity.OrgHierarchy;
import com.vaadin.hummingbird.complexform.model.entity.ScorecardEntity;

@SuppressWarnings("serial")
public class ScorecardUtil {

    private static final String MONTHLY_FREQUENCY_FORMAT = "MMM yyyy";

    private final static NumberValidator numberValidator = new NumberValidator();
    private final static CurrencyValidator currencyValidator = new CurrencyValidator();
    private final static PercentageValidator percentageValidator = new PercentageValidator();

    private final static UnitTypeConverter UNIT_TYPE_CONVERTER = new UnitTypeConverter();

    public static DateFormat getDefaultDateFormat(Locale locale) {
        return SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT,
                locale);
    }

    public static DateFormat getMonthYearDateFormat(Locale locale) {
        return new SimpleDateFormat(MONTHLY_FREQUENCY_FORMAT, locale);
    }

    public static List<ScorecardEntity> getUpdatedSelection(
            Collection<Object> oldSelected,
            Collection<ScorecardEntity> newValues) {
        ArrayList<ScorecardEntity> newSelected = new ArrayList<ScorecardEntity>();
        for (Iterator<Object> i = oldSelected.iterator(); i.hasNext();) {
            ScorecardEntity old = (ScorecardEntity) i.next();
            for (ScorecardEntity entity : newValues) {
                if (entity instanceof Goal) {
                    if (((Goal) old)
                            .getGoalId() == (((Goal) entity).getGoalId())) {
                        newSelected.add(entity);
                        break;
                    }
                }
            }
        }
        return newSelected;
    }

    public static OrgHierarchy getRootOrganization() {
        OrgHierarchy orgHierarchy = new OrgHierarchy();
        orgHierarchy.setEntityName("No Parent (Root level)");
        orgHierarchy.setId(0L);
        return orgHierarchy;
    }

    /**
     * Builds a pretty format for the given date and frequency.
     *
     * @param date
     * @param frequency
     *            either 'A', 'Q' or 'M'.
     * @return
     */
    public static String buildPrettyString(Date date, String frequency) {
        final Calendar USCAL = Calendar.getInstance(Locale.US);
        USCAL.setTime(date);
        if ("A".equals(frequency)) {
            return Integer.toString(USCAL.get(Calendar.YEAR));
        } else if ("Q".equals(frequency)) {
            final int quater = (USCAL.get(Calendar.MONTH) / 3) + 1;
            return new StringBuilder().append(USCAL.get(Calendar.YEAR))
                    .append("Q").append(quater).toString();
        } else if (("M").equals(frequency)) {
            return getMonthYearDateFormat(Locale.US).format(date);
        }
        return "Invalid frequency type (" + frequency
                + "), could not parse to date";
    }

    public static String getCurrentQuaterPeriodDesc() {
        final Calendar cal = Calendar.getInstance(Locale.US);
        final int quater = (cal.get(Calendar.MONTH) / 3) + 1;
        return new StringBuilder().append(cal.get(Calendar.YEAR)).append("Q")
                .append(quater).toString();
    }

    public static String getCurrentMonthPeriodDesc() {
        return getMonthYearDateFormat(Locale.US).format(new Date());

    }

    public static NumberValidator getNumbervalidator() {
        return numberValidator;
    }

    public static CurrencyValidator getCurrencyvalidator() {
        return currencyValidator;
    }

    public static Validator getPercentagevalidator() {
        return percentageValidator;
    }

    public static Converter<?, ?> getUnitTypeConverter() {
        return UNIT_TYPE_CONVERTER;
    }
}
