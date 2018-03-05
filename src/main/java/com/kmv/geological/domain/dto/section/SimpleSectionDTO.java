package com.kmv.geological.domain.dto.section;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SimpleSectionDTO {

    private Long id;
    private String name;

    public SimpleSectionDTO() {
    }

    public SimpleSectionDTO(Long id, String name) {
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

}
