package se.lexicon.jomian.util;

import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This returns the message from the language key so we can have programmatically defined error message where
 * EL expressions are not available.
 *
 * @author johan
 * @since 2016-09-01.
 */
public class Language {
    /**
     * Returns the message for the specified key.
     *
     * @param key the key defined in the language resource file.
     * @return the defined message for the specified key.
     */
    public static String getMessage(String key) {
        Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        ResourceBundle text = ResourceBundle.getBundle("language", currentLocale);
        return text.getString(key);
    }
}
