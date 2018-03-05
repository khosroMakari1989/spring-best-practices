package com.kmv.geological.domain.entity;

import com.kmv.geological.domain.entity.enums.JobStatus;
import com.kmv.geological.domain.entity.enums.JobType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * This Entity is a general table for jobs. So, no relation to sectionEntity
 * added here for loose coupling. Also, Spring Batch could be used for jobs,
 * however, for simplicity and the first version, simple job mechanism is
 * implemented in preference to spring batch.
 *
 * @author khosro.makari@gmail.com
 */
@Table(name = "jobs", uniqueConstraints = @UniqueConstraint(name = "UNIQUE_JOBS_NAME", columnNames = {"name"}))
@Entity
public class JobEntity extends BaseEntity {

    /**
     * Name is optional. For file-based jobs we put the name of the file.
     */
    @Column(length = 128)
    private String name;

    //JPA Converter could be used here to store small long, but for simplicity the string is saved.
    @Column(length = 128)
    @Enumerated(EnumType.STRING)
    private JobType type;

    @Column(length = 128)
    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.STARTED;

    @Column(length = 512)
    private String description;

    public JobEntity() {
    }

    public JobEntity(String name, JobType type, JobStatus status) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
