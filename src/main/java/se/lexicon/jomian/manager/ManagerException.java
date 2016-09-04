package se.lexicon.jomian.manager;

/**
 * This exception is used to pass through errors from managers to the calling JSF bean.
 *
 * @author johan
 * @since 2016-09-01.
 */
public class ManagerException extends Exception {
    public ManagerException(String message) {
        super(message);
    }
}
