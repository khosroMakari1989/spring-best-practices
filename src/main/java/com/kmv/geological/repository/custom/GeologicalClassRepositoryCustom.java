package com.kmv.geological.repository.custom;

import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface GeologicalClassRepositoryCustom {

    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(GeologicalClassFilterRequestDTO requestDTO);
}
