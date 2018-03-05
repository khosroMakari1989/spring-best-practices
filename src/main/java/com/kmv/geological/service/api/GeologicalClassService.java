package com.kmv.geological.service.api;

import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface GeologicalClassService {

    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(GeologicalClassFilterRequestDTO requestDTO);

}
