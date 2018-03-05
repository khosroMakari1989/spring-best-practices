package com.kmv.geological.service.api;

import com.kmv.geological.domain.dto.page.SimplePageRequestDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;
import com.kmv.geological.domain.dto.section.SectionByJobRequestDTO;
import com.kmv.geological.domain.dto.section.SectionWithGeologicals;
import com.kmv.geological.domain.dto.section.SimpleSectionDTO;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface SectionService {

    public SectionWithGeologicals saveOrUpdate(SectionWithGeologicals entity);

    public void remove(Long id);

    public SectionWithGeologicals findById(Long id);

    public SimplePageResponseDTO<SimpleSectionDTO> findAll(SimplePageRequestDTO pageRequest);

    public SimplePageResponseDTO<SimpleSectionDTO> findAllByJob(SectionByJobRequestDTO requestDTO);

}
