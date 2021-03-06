package se.lexicon.jomian.util;

import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This returns the message from the language key so we can have programmatically defined message message where
 * EL expressions are not available.
 *
 * @author Johan Gustafsson
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

    public static String getFormatedMessage(String key, Object... args) {
        String message = getMessage(key);
        MessageFormat messageFormat = new MessageFormat(message);
        return messageFormat.format(args);
    }
}
