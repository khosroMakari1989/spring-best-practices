package com.kmv.geological.controller;

import com.kmv.geological.aspect.CheckBindingResult;
import com.kmv.geological.domain.dto.page.SimplePageRequestDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;
import com.kmv.geological.domain.dto.section.SectionWithGeologicals;
import com.kmv.geological.domain.dto.section.SimpleSectionDTO;
import com.kmv.geological.service.api.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author khosro.makari@gmail.com
 */
@RestController
@RequestMapping(value = "/section")
public class SectionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SectionController.class.getName());

    @Autowired
    private SectionService sectionService;

    @CheckBindingResult
    @PostMapping
    public SectionWithGeologicals createOrEditSecion(@RequestBody @Validated SectionWithGeologicals sectionDTO, BindingResult bindingResult) {
        LOGGER.info("SECTION ==>  ID: {}, NAME: {} ", sectionDTO.getId(), sectionDTO.getName());
        return sectionService.saveOrUpdate(sectionDTO);
    }

    @CheckBindingResult
    @PostMapping("/details")
    public SectionWithGeologicals searchById(@RequestBody Long id) {
        LOGGER.info("SECTION ID: ", id);
        return sectionService.findById(id);
    }

    @CheckBindingResult
    @GetMapping
    public SimplePageResponseDTO<SimpleSectionDTO> findAll(@Validated SimplePageRequestDTO pageRequest, BindingResult bindingResult) {
        LOGGER.info("PAGEREQUEST ===> PAGE: {}, SIZE: {}", pageRequest.getPage(), pageRequest.getSize());
        return sectionService.findAll(pageRequest);
    }

    @DeleteMapping
    public void remove(@RequestBody Long id) {
        LOGGER.info("SECTION ID :", id);
        sectionService.remove(id);
    }

}
