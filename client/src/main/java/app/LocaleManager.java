package app;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {

    private ResourceBundle bundle;
    private Locale locale;

    public LocaleManager() {
        locale = Locale.forLanguageTag("ru");
        bundle = ResourceBundle.getBundle("gui.properties.Resources", locale);
    }

    public void changeLocale(String localeName) {
        this.locale = Locale.forLanguageTag(localeName);
        bundle = ResourceBundle.getBundle("gui.properties.Resources", locale);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public String formatNumber(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return numberFormat.format(number);
    }

    public String formatDate(LocalDate date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        Date castedDate = java.util.Date.from(LocalDate.parse(date.toString()).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
        return dateFormat.format(castedDate);
    }

    public String formatDate(Object date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return dateFormat.format(date);
    }
}
