package se.lexicon.jomian.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Map;

/**
 * @author Johan Gustafsson
 * @since 2016-09-10.
 */
public class CurrentContext {
    public static void message(String id, String message) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(message));
    }

    public static void redirect(String url) throws IOException {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + url);
    }

    public static Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
}
