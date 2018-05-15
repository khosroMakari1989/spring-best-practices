package com.kmv.geological.dto.page;

import com.kmv.geological.dto.BaseRequestDTO;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SimplePageRequestDTO extends BaseRequestDTO {

    @Min(0)
    private int page;
    @Min(1)
    @Max(40)
    private int size = 30;

    public SimplePageRequestDTO() {
    }

    public SimplePageRequestDTO(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
