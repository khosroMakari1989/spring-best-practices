package com.kmv.geological.service.api;

import com.kmv.geological.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.dto.page.SimplePageResponseDTO;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface GeologicalClassService {

    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(GeologicalClassFilterRequestDTO requestDTO);

}
