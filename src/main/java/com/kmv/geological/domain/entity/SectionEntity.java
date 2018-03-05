package com.kmv.geological.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 1- database h2 or sqllight 2- spring batch 3- spring data rest and spring
 * data jpa 4-ignore the item 9 5- webflux 6- log the results
 *
 * table job - id, status
 *
 * @author khosro.makari@gmail.com
 */
@Entity
@Table(name = "sections", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"}, name = "UNIQUE_SECTION_NAME")}, indexes = {
    @Index(name = "INDEX_SECTION_NAME", columnList = "name")})
public class SectionEntity extends BaseEntity {

    //use id&uniqueKey for equals and hashCode
    @NotBlank
    @Column(nullable = false, length = 128)
    private String name;

    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "FK_SCTIONS_TO_GELOGICALCLASS"))
    @OneToMany(cascade = CascadeType.ALL)
    private List<GeologicalClassEntity> geologicalClasses = new ArrayList<>();

    @JoinColumn(name = "job_id", foreignKey = @ForeignKey(name = "FK_JOBS_TO_SECTIONS"))
    @ManyToOne(fetch = FetchType.LAZY)
    private JobEntity job;

    public SectionEntity() {
    }

    public SectionEntity(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeologicalClassEntity> getGeologicalClasses() {
        return geologicalClasses;
    }

    public void setGeologicalClasses(List<GeologicalClassEntity> geologicalClasses) {
        this.geologicalClasses = geologicalClasses;
    }

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }

}
