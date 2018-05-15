package com.kmv.geological.controller;

import com.kmv.geological.aspect.CheckBindingResult;
import com.kmv.geological.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.dto.page.SimplePageResponseDTO;
import com.kmv.geological.service.api.GeologicalClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author khosro.makari@gmail.com
 */
@RestController
public class GeologicalClassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeologicalClassController.class.getName());

    @Autowired
    private GeologicalClassService geologicalClassService;

    @CheckBindingResult
    @GetMapping("/geologicals/filter")
    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(@Validated GeologicalClassFilterRequestDTO requestDTO, BindingResult bindingResult) {
        LOGGER.info("requestDTO ==>  code : {}, name : {}", requestDTO.getCode(), requestDTO.getName());
        return geologicalClassService.filter(requestDTO);
    }
}
