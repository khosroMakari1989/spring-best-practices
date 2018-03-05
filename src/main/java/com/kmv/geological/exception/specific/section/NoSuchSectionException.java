package com.kmv.geological.exception.specific.section;

import com.kmv.geological.exception.general.NoSuchResourceException;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class NoSuchSectionException extends NoSuchResourceException {

    public NoSuchSectionException(String message) {
        super(message);
    }

    public NoSuchSectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
