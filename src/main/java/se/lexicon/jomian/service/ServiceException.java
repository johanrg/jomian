package se.lexicon.jomian.service;

/**
 * This exception is used to pass through errors from managers to the calling JSF bean.
 *
 * @author Johan Gustafsson
 * @since 2016-09-01.
 */
public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}
