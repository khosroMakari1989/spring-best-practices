package com.kmv.geological.exception.specific.job;

import com.kmv.geological.exception.general.NoSuchResourceException;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class NoSuchJobException extends NoSuchResourceException {

    public NoSuchJobException(String message) {
        super(message);
    }

    public NoSuchJobException(String message, Throwable cause) {
        super(message, cause);
    }

}
