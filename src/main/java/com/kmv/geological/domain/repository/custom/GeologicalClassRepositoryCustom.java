package com.kmv.geological.domain.repository.custom;

import com.kmv.geological.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.dto.page.SimplePageResponseDTO;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface GeologicalClassRepositoryCustom {

    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(GeologicalClassFilterRequestDTO requestDTO);
}
