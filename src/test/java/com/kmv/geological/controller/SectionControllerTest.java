package com.kmv.geological.controller;

import com.kmv.geological.config.JacksonMapperConfig;
import com.kmv.geological.dto.section.SectionWithGeologicals;
import com.kmv.geological.domain.entity.enums.JobStatus;
import com.kmv.geological.domain.entity.enums.JobType;
import com.kmv.geological.BaseTest;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class SectionControllerTest extends BaseTest {

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

    @Test
    public void post_simpleSection_ok() throws Exception {
        //arrange
        SectionWithGeologicals requestDTO = new SectionWithGeologicals("rest section");

        //act and asserts
        mvc.perform(post("/section")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void post_emptySection_badRequest() throws Exception {
        //arrange
        SectionWithGeologicals requestDTO = new SectionWithGeologicals();

        //act and asserts
//        MvcResult mvcResult =
        mvc.perform(post("/section")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    //409 for trying to add duplicate record
    @Test
    public void post_duplicateSection_conflictCode() throws Exception {
        //arrange
        SectionWithGeologicals requestDTO = new SectionWithGeologicals("Section 1");

        //act and asserts
        mvc.perform(post("/section")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

    }

    @Test
    public void post_nonExistingSection_notFoundCode() throws Exception {
        //arrange
        SectionWithGeologicals requestDTO = new SectionWithGeologicals(Long.MAX_VALUE, "Section doesn't exist");

        //act and asserts
        mvc.perform(post("/section")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void searchById_existingSection_ok() throws Exception {
        //arrange
        Long id = 1L;

        //act and asserts
        mvc.perform(post("/section/details")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void searchById_sectionByZeroId_notFound() throws Exception {
        //arrange
        Long id = 0L;

        //act and asserts
        MvcResult result = mvc.perform(post("/section/details")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void searchById_nonExistingSetion_notFound() throws Exception {
        //arrange
        Long id = Long.MAX_VALUE;

        //act and asserts
        mvc.perform(post("/section/details")
                .content(JacksonMapperConfig.getObjectMapper().writeValueAsString(id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    ///Rest of the tests...
}
