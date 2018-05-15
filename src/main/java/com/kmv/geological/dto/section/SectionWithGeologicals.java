package com.kmv.geological.dto.section;

import com.kmv.geological.dto.geologicalclass.GeologicalClassResponseDTO;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SectionWithGeologicals implements Serializable {

    @Min(1)
    private Long id;
    @NotBlank
    private String name;

    private List<GeologicalClassResponseDTO> geologicalClassList;

    public SectionWithGeologicals() {
    }

    public SectionWithGeologicals(String name) {
        this.name = name;
    }

    public SectionWithGeologicals(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeologicalClassResponseDTO> getGeologicalClassList() {
        return geologicalClassList;
    }

    public void setGeologicalClassList(List<GeologicalClassResponseDTO> geologicalClassList) {
        this.geologicalClassList = geologicalClassList;
    }

}
