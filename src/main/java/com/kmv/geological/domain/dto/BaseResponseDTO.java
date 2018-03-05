package com.kmv.geological.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class BaseResponseDTO implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
