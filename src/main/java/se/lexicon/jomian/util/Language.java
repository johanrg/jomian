package se.lexicon.jomian.util;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author johan
 * @since 2016-09-01.
 */
public class Language {
    public static String getMessage(String key) {
        Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle text = ResourceBundle.getBundle("language", currentLocale);
        return text.getString(key);
    }
}
