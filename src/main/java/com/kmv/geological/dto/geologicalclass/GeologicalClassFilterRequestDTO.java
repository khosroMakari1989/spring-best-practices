package com.kmv.geological.dto.geologicalclass;

import com.kmv.geological.dto.page.SimplePageRequestDTO;
import javax.validation.constraints.Size;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class GeologicalClassFilterRequestDTO extends SimplePageRequestDTO {

    @Size(max = 128)
    private String name;

    @Size(max = 64)
    private String code;

    public GeologicalClassFilterRequestDTO() {
    }

    public GeologicalClassFilterRequestDTO(String name, String code, int page, int size) {
        super(page, size);
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
