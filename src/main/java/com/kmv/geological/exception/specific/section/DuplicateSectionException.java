package com.kmv.geological.exception.specific.section;

import com.kmv.geological.exception.general.DuplicateResourceException;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class DuplicateSectionException extends DuplicateResourceException {

    public DuplicateSectionException(String message) {
        super(message);
    }

    public DuplicateSectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
