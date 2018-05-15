package com.kmv.geological.dto.section;

import com.kmv.geological.dto.page.SimplePageRequestDTO;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SectionByJobRequestDTO extends SimplePageRequestDTO {

    @Min(1)
    @NotNull
    private Long jobId;

    public SectionByJobRequestDTO() {
    }

    public SectionByJobRequestDTO(Long jobId, int page, int size) {
        super(page, size);
        this.jobId = jobId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

}
