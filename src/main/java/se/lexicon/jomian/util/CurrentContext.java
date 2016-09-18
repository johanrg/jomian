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

    public static void redirect(String url) {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + url);
        } catch (IOException e) {
            // yeah I don't really care that much in this case, just shut up.
        }
    }

    public static void redirect404() {
        redirect("/WEB-INF/errorpage/404.xml");
    }

    public static Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
}
