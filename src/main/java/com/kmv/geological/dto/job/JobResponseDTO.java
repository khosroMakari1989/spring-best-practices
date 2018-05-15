package com.kmv.geological.dto.job;

import com.kmv.geological.dto.BaseResponseDTO;
import com.kmv.geological.domain.entity.enums.JobStatus;
import com.kmv.geological.domain.entity.enums.JobType;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class JobResponseDTO extends BaseResponseDTO {

    private String name;

    private JobType type;

    private JobStatus status;

    public JobResponseDTO() {
    }

    public JobResponseDTO(String name, JobType type, JobStatus status) {
        this.name = name;
        this.type = type;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

}
