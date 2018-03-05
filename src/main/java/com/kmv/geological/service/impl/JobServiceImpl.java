package com.kmv.geological.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kmv.geological.config.JacksonMapperConfig;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.domain.dto.job.JobResponseDTO;
import com.kmv.geological.domain.entity.GeologicalClassEntity;
import com.kmv.geological.domain.entity.JobEntity;
import com.kmv.geological.domain.entity.SectionEntity;
import com.kmv.geological.domain.entity.enums.JobStatus;
import com.kmv.geological.exception.BusinessException;
import com.kmv.geological.exception.specific.job.NoSuchJobException;
import com.kmv.geological.repository.api.JobRepository;
import com.kmv.geological.repository.api.SectionRepository;
import com.kmv.geological.service.api.JobService;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author khosro.makari@gmail.com
 */
@Service
public class JobServiceImpl implements JobService {

    private static final Logger LOGGER = Logger.getLogger(JobServiceImpl.class.getName());
    private final JobRepository jobRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, SectionRepository sectionRepository) {
        this.jobRepository = jobRepository;
        this.sectionRepository = sectionRepository;
    }

    @Override
    @Transactional
    public JobEntity initJob(String name) {
        JobEntity jobEntity = new JobEntity();
        String uniqueName = new StringBuilder(name.length() > 60 ? name.substring(0, 60) : name).append("-").
                append(RandomStringUtils.randomAlphabetic(10)).
                append("-").append(String.valueOf(System.currentTimeMillis())).toString();
        jobEntity.setName(uniqueName);
        try {
            jobRepository.save(jobEntity);
            return jobEntity;
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

    //TODO: To be more optimized and more tested and breaked into helper methods! Spring Batch can be used in preference to this method.
    @Transactional
    @Override
    public void processExcelFile(JobEntity jobEntity, InputStream ioStream) {
        try (InputStream inputStream = ioStream) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            //TODO: check that all rows inserted or not
            long rowCount = workbook.getSheetAt(0).getLastRowNum();
            if (rowCount < 2) {
                jobEntity.setStatus(JobStatus.NO_CONTENT);
                jobRepository.save(jobEntity);
                return;
            }
            Iterator<Row> iterator = workbook.getSheetAt(0).iterator();
            //to skip the first row which is titles
            iterator.next();
            //simple size for batch save
            List<SectionEntity> sectionEntities = new ArrayList<>(100);
            boolean isListSaved = false;
            jobEntity.setStatus(JobStatus.IN_PROGRESS);
            jobRepository.save(jobEntity);
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null && currentRow.getCell(0).getCellTypeEnum() != CellType.STRING) {
                    continue;
                }
                SectionEntity sectionEntity = new SectionEntity(currentRow.getCell(0).getStringCellValue());
                sectionEntity.setJob(jobEntity);
                Cell geoLogicalClassCell = currentRow.getCell(1);
                if (geoLogicalClassCell != null) {
                    GeologicalClassResponseDTO[] gcdtos = JacksonMapperConfig.getObjectMapper().readValue(geoLogicalClassCell.getStringCellValue(), GeologicalClassResponseDTO[].class);
                    List<GeologicalClassEntity> list = Stream.of(gcdtos).map(dto -> new GeologicalClassEntity(dto.getName(), dto.getCode())).collect(Collectors.toList());
                    sectionEntity.setGeologicalClasses(list);
                }
                sectionEntities.add(sectionEntity);
                if (sectionEntities.size() == 100) {
                    sectionRepository.save(sectionEntities);
                    isListSaved = true;
                    sectionEntities.clear();
                }
            }
            //If the loop is finished and the list size < 100, then this will insert the data
            if (!isListSaved && !sectionEntities.isEmpty()) {
                sectionRepository.save(sectionEntities);
            }

            jobEntity.setStatus(JobStatus.COMPLETED);
            //TODO: change the job status based on the type of caught exception
        } catch (JsonProcessingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            jobEntity.setStatus(JobStatus.FAILED);
            jobEntity.setDescription(ex.getMessage());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            jobEntity.setStatus(JobStatus.FAILED);
            jobEntity.setDescription(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            jobEntity.setStatus(JobStatus.FAILED);
            jobEntity.setDescription(ex.getMessage());
        }
        jobRepository.save(jobEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponseDTO findJob(Long id) {
        if (id == null || id < 1) {
            throw new NoSuchJobException("Job not found!");
        }
        try {
            JobEntity jobEntity = jobRepository.findOne(id);
            if (jobEntity == null) {
                throw new NoSuchJobException("Job not found!");
            }
            return new JobResponseDTO(jobEntity.getName(), jobEntity.getType(), jobEntity.getStatus());
        } catch (DataAccessException ex) {
            throw new BusinessException(ex.getMessage(), ex);
        }
    }

}
