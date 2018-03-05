package com.kmv.geological.service.impl;

import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;
import com.kmv.geological.exception.BusinessException;
import com.kmv.geological.repository.api.GeologicalClassRepository;
import com.kmv.geological.service.api.GeologicalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author khosro.makari@gmail.com
 */
@Service
public class GeologicalClassServiceImpl implements GeologicalClassService {

    private final GeologicalClassRepository geologicalClassRepository;

    @Autowired
    public GeologicalClassServiceImpl(GeologicalClassRepository geologicalClassRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(GeologicalClassFilterRequestDTO requestDTO) {
        try {
            return geologicalClassRepository.filter(requestDTO);
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }

    }

}
