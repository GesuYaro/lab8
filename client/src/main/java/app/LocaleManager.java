package app;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {

    private ResourceBundle bundle;
    private Locale locale;

    public LocaleManager() {
        locale = Locale.forLanguageTag("ru");
        bundle = ResourceBundle.getBundle("gui.properties.Resources", locale);
    }

    public void changeLocale(Locale locale) {
        this.locale = locale;
        bundle = ResourceBundle.getBundle("Resources", locale);
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
        return dateFormat.format(date);
    }

    public String formatDate(Object date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return dateFormat.format(date);
    }
}
