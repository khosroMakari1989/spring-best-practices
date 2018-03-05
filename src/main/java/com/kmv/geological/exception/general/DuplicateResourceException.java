package com.kmv.geological.exception.general;

import com.kmv.geological.exception.BusinessException;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
