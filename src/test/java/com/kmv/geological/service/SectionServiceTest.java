package com.kmv.geological.service;

import com.kmv.geological.BaseTest;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.domain.dto.page.SimplePageRequestDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;
import com.kmv.geological.domain.dto.section.SectionByJobRequestDTO;
import com.kmv.geological.domain.dto.section.SectionWithGeologicals;
import com.kmv.geological.domain.dto.section.SimpleSectionDTO;
import com.kmv.geological.domain.entity.enums.JobStatus;
import com.kmv.geological.domain.entity.enums.JobType;
import com.kmv.geological.exception.specific.section.DuplicateSectionException;
import com.kmv.geological.exception.specific.section.NoSuchSectionException;
import com.kmv.geological.service.api.SectionService;
import java.util.Arrays;
import java.util.Date;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SectionServiceTest extends BaseTest {

    @Autowired
    private SectionService sectionService;

    @Before
    public void init() {
        jdbcTemplate.update("insert into jobs(id, name, status, type, created_at) values(?, ?, ?, ?, ?)",
                1, "FileProcessJob", JobStatus.STARTED.name(), JobType.FILE_READING.name(), new Date());
        jdbcTemplate.update("insert into sections(id, name, job_id, created_at) values(?, ?, ?, ?)", 1, "Section 1", 1, new Date());
        jdbcTemplate.update("insert into sections(id, name, job_id, created_at) values(?, ?, ?, ?)", 2, "Section 2", 1, new Date());
        jdbcTemplate.update("insert into sections(id, name, job_id, created_at) values(?, ?, ?, ?)", 3, "Section 3", null, new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                1, "geo1", "code1", 1, new Date());
    }

    //unitOfWork_behavoirState_expectedResult
    @Test
    public void save_simpleSection_succeed() {
        //arrange
        SectionWithGeologicals sectionEntity = new SectionWithGeologicals("Section_save");
        SectionWithGeologicals expectedEntity = null;

        //act
        expectedEntity = sectionService.saveOrUpdate(sectionEntity);

        //asserts
        Assert.assertNotEquals(expectedEntity, null);
        Assert.assertEquals(expectedEntity.getName(), sectionEntity.getName());
        Assert.assertNotNull(expectedEntity.getId());
    }

    @Test
    public void save_sectionWithGeologicals_succeed() {
        //arrange
        SectionWithGeologicals sectionEntity = new SectionWithGeologicals("Section_save");
        sectionEntity.setGeologicalClassList(Arrays.asList(new GeologicalClassResponseDTO("Class 1", "CG1"), new GeologicalClassResponseDTO("Class 2", "CG2")));
        SectionWithGeologicals expectedEntity = null;

        //act
        expectedEntity = sectionService.saveOrUpdate(sectionEntity);

        //asserts
        Assert.assertNotEquals(expectedEntity, null);
        Assert.assertEquals(expectedEntity.getName(), sectionEntity.getName());
        Assert.assertNotNull(expectedEntity.getGeologicalClassList());
        Assert.assertEquals(expectedEntity.getGeologicalClassList().size(), 2);
    }

    @Test(expected = DuplicateSectionException.class)
    public void save_duplicateSectionName_throwException() {
        //arrange
        SectionWithGeologicals sectionEntity = new SectionWithGeologicals("Section_save");
        SectionWithGeologicals duplicateSection = new SectionWithGeologicals("Section_save");

        //act
        sectionService.saveOrUpdate(sectionEntity);
        sectionService.saveOrUpdate(duplicateSection);
    }

    @Test
    public void update_secionName_succeed() {
        //arrange
        String updatedSctionName = "Section Updated";
        SectionWithGeologicals sectionDTO = new SectionWithGeologicals(updatedSctionName);
        sectionDTO.setId(1L);
        SectionWithGeologicals expectedSecion = null;

        //act
        expectedSecion = sectionService.saveOrUpdate(sectionDTO);

        //asserts
        Assert.assertNotEquals(expectedSecion, null);
        Assert.assertEquals(expectedSecion.getName(), sectionDTO.getName());
        Assert.assertEquals(expectedSecion.getId(), sectionDTO.getId());
    }

    @Test
    public void update_GeologicalClasses_succeed() {
        //arrange
        String updatedSctionName = "Section 1";
        SectionWithGeologicals sectionDTO = new SectionWithGeologicals(updatedSctionName);
        sectionDTO.setId(1L);
        sectionDTO.setGeologicalClassList(Arrays.asList(new GeologicalClassResponseDTO(1L, "Class 1", "code1"), new GeologicalClassResponseDTO("Class 2", "CG2")));

        SectionWithGeologicals expectedSecion = null;

        //act
        expectedSecion = sectionService.saveOrUpdate(sectionDTO);

        //asserts
        Assert.assertNotEquals(expectedSecion, null);
        Assert.assertEquals(expectedSecion.getName(), sectionDTO.getName());
        Assert.assertEquals(expectedSecion.getId(), sectionDTO.getId());
        Assert.assertNotEquals(expectedSecion.getGeologicalClassList(), null);
        Assert.assertEquals(2, expectedSecion.getGeologicalClassList().size());
        Assert.assertEquals("Class 1", expectedSecion.getGeologicalClassList().get(0).getName());
    }

    @Test(expected = NoSuchSectionException.class)
    public void update_nonExistingSection_throwsException() {
        //arrange
        String updatedSctionName = "Section Updated";
        SectionWithGeologicals sectionDTO = new SectionWithGeologicals(updatedSctionName);
        sectionDTO.setId(Long.MIN_VALUE);
        SectionWithGeologicals expectedSecion = null;

        //act
        expectedSecion = sectionService.saveOrUpdate(sectionDTO);
    }

    ///Do more tests for save and update! Things are not as easy as they seem!
    @Test
    public void remove_existingSection_succeed() {
        //arrange
        Long id = 1L;

        //act
        sectionService.remove(id);
    }

    @Test(expected = NoSuchSectionException.class)
    public void remove_nonExistingSection_throwException() {
        //arrange
        Long id = Long.MAX_VALUE;

        //act
        sectionService.remove(id);
    }

    @Test
    public void findById_existingSection_succeed() {
        //arrange
        Long id = 1L;
        SectionWithGeologicals expectedSection = null;

        //act
        expectedSection = sectionService.findById(id);

        //asserts
        Assert.assertNotNull(expectedSection);
        Assert.assertEquals(id, expectedSection.getId());
        Assert.assertNotNull(expectedSection.getGeologicalClassList());
    }

    @Test(expected = NoSuchSectionException.class)
    public void findById_nonExistingSection_succeed() {
        //arrange
        Long id = Long.MAX_VALUE;

        //act
        sectionService.findById(id);

    }

    @Test
    public void findAll_simplePage_succeed() {
        //arrange
        SimplePageRequestDTO pageRequest = new SimplePageRequestDTO(0, 10);
        SimplePageResponseDTO<SimpleSectionDTO> pageResponse;

        //act
        pageResponse = sectionService.findAll(pageRequest);

        //asserts
        Assert.assertNotNull(pageResponse);
        Assert.assertThat(pageResponse.getCount(), Matchers.greaterThan(0L));
        Assert.assertNotNull(pageResponse.getContent());
        Assert.assertThat(pageResponse.getContent().size(), Matchers.greaterThan(0));
    }

    @Test
    public void findAll_pageNumberMoreThanExisting_emptyResult() {
        //arrange
        SimplePageRequestDTO pageRequest = new SimplePageRequestDTO(10000, 10);
        SimplePageResponseDTO<SimpleSectionDTO> pageResponse;

        //act
        pageResponse = sectionService.findAll(pageRequest);

        //asserts
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(0, pageResponse.getCount());
        Assert.assertNull(pageResponse.getContent());
    }

    @Test
    public void findAllByJob_simplePageWithExistingJob_succeed() {
        //arrange
        SectionByJobRequestDTO requestDTO = new SectionByJobRequestDTO(1L, 0, 10);
        SimplePageResponseDTO<SimpleSectionDTO> pageResponse;

        //act
        pageResponse = sectionService.findAllByJob(requestDTO);

        //asserts
        Assert.assertNotNull(pageResponse);
        Assert.assertThat(pageResponse.getCount(), Matchers.greaterThan(0L));
        Assert.assertNotNull(pageResponse.getContent());
        Assert.assertThat(pageResponse.getContent().size(), Matchers.greaterThan(0));
    }

}
