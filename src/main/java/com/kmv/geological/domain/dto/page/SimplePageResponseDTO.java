package com.kmv.geological.domain.dto.page;

import com.kmv.geological.domain.dto.BaseResponseDTO;
import java.util.List;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SimplePageResponseDTO<T extends Object> extends BaseResponseDTO {

    private List<T> content;
    private long count;

    public List<T> getContent() {
        return content;
    }

    public SimplePageResponseDTO() {
    }

    public SimplePageResponseDTO(List<T> content, long count) {
        this.content = content;
        this.count = count;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}
