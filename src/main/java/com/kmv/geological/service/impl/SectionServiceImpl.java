package com.kmv.geological.service.impl;

import com.kmv.geological.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.dto.page.SimplePageRequestDTO;
import com.kmv.geological.dto.page.SimplePageResponseDTO;
import com.kmv.geological.dto.section.SectionByJobRequestDTO;
import com.kmv.geological.dto.section.SectionWithGeologicals;
import com.kmv.geological.dto.section.SimpleSectionDTO;
import com.kmv.geological.domain.entity.GeologicalClassEntity;
import com.kmv.geological.domain.entity.SectionEntity;
import com.kmv.geological.exception.BusinessException;
import com.kmv.geological.exception.specific.section.DuplicateSectionException;
import com.kmv.geological.exception.specific.section.NoSuchSectionException;
import com.kmv.geological.domain.repository.api.SectionRepository;
import com.kmv.geological.service.api.SectionService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author khosro.makari@gmail.com
 */
@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Transactional
    @Override
    public SectionWithGeologicals saveOrUpdate(SectionWithGeologicals sectionDTO) {
        try {
            SectionEntity entity = sectionDTO.getId() != null ? sectionRepository.findOne(sectionDTO.getId()) : new SectionEntity(sectionDTO.getName());
            if (entity == null) {
                throw new NoSuchSectionException("no such section to be found!");
            }
            if (sectionDTO.getGeologicalClassList() != null) {
                entity.setGeologicalClasses(sectionDTO.getGeologicalClassList().stream().
                        map(dto -> new GeologicalClassEntity(dto.getId(), dto.getName(), dto.getCode())).collect(Collectors.toList()));
            }
            entity = sectionRepository.save(entity);
            sectionDTO.setId(entity.getId());
            return sectionDTO;
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateSectionException(ex.getMessage(), ex);
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @Transactional
    @Override
    public void remove(Long id) {
        try {
            sectionRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchSectionException("no such section for remove!", ex);
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public SectionWithGeologicals findById(Long id) {
        if (id == null || id < 1) {
            throw new NoSuchSectionException("no such section to be found!");
        }
        try {
            SectionEntity entity = sectionRepository.findOne(id);
            if (entity == null) {
                throw new NoSuchSectionException("no such section to be found!");
            }
            SectionWithGeologicals sectionDTO = new SectionWithGeologicals(entity.getId(), entity.getName());
            if (entity.getGeologicalClasses() != null) {
                sectionDTO.setGeologicalClassList(entity.getGeologicalClasses().stream()
                        .map(g -> new GeologicalClassResponseDTO(g.getId(), g.getName(), g.getCode())).collect(Collectors.toList()));
            }
            return sectionDTO;
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public SimplePageResponseDTO<SimpleSectionDTO> findAll(SimplePageRequestDTO pageRequest) {
        PageRequest request = new PageRequest(pageRequest.getPage(), pageRequest.getSize());
        SimplePageResponseDTO<SimpleSectionDTO> simplePageResponse = new SimplePageResponseDTO<>();
        try {
            Page<SectionEntity> page = sectionRepository.findAll(request);
            if (page.getContent().isEmpty()) {
                return simplePageResponse;
            }
            simplePageResponse.setCount((long) page.getTotalElements());
            simplePageResponse.setContent(page.getContent().stream()
                    .map(entity -> new SimpleSectionDTO(entity.getId(), entity.getName())).collect(Collectors.toList()));
            return simplePageResponse;
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public SimplePageResponseDTO<SimpleSectionDTO> findAllByJob(SectionByJobRequestDTO requestDTO) {
        SimplePageResponseDTO<SimpleSectionDTO> simplePageResponse = new SimplePageResponseDTO<>();
        PageRequest pageRequest = new PageRequest(requestDTO.getPage(), requestDTO.getSize());
        try {
            Page<SectionEntity> page = sectionRepository.findByJobId(requestDTO.getJobId(), pageRequest);
            if (page.getContent().isEmpty()) {
                return simplePageResponse;
            }
            simplePageResponse.setCount((long) page.getTotalElements());
            simplePageResponse.setContent(page.getContent().stream()
                    .map(entity -> new SimpleSectionDTO(entity.getId(), entity.getName())).collect(Collectors.toList()));
            return simplePageResponse;
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

}
