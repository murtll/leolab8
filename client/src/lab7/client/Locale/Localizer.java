package lab7.client.Locale;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {
    private static final Locale[] availableLocales = new Locale[]{
            new Locale("ru", "RU"),
            new Locale("el", "GR"),
            new Locale("es", "ES"),
            new Locale("nl", "NL")
    };
    private static Locale locale = availableLocales[0];
    private static final String baseName = "localization.dictionary";
    private static ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale);

    public static void setLocale(Locale locale) {
        if (Arrays.stream(availableLocales).noneMatch(l -> l.equals(locale))) {
            throw new RuntimeException("Locale " + locale + " is not supported");
        }

        Localizer.locale = locale;
        bundle = ResourceBundle.getBundle(baseName, locale);
    }

    public static String getLocalizedText(String key) {
        try {
            if (bundle.keySet().contains(key)) {
                return bundle.getString(key);
            } else {
                return key;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return key;
        }
    }

    public static Locale getLocale() {
        return locale;
    }

    public static Locale[] getAvailableLocales() {
        return availableLocales;
    }
}
