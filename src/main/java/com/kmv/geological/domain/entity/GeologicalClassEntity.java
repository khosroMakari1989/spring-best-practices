package com.kmv.geological.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author khosro.makari@gmail.com
 */
@Entity
@Table(name = "geologicalclasses")
public class GeologicalClassEntity extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 128)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 64)
    private String code;

    public GeologicalClassEntity() {
    }

    public GeologicalClassEntity(Long id, String name, String code) {
        super(id);
        this.name = name;
        this.code = code;
    }

    public GeologicalClassEntity(String name, String code) {
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
