package com.kmv.geological.controller;

import com.kmv.geological.config.JacksonMapperConfig;
import com.kmv.geological.domain.dto.job.JobResponseDTO;
import com.kmv.geological.domain.entity.enums.JobStatus;
import com.kmv.geological.domain.entity.enums.JobType;
import com.kmv.geological.BaseTest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class JobControllerTest extends BaseTest {

    @Before
    public void init() {
        jdbcTemplate.update("insert into jobs(id, name, status, type, created_at) values(?, ?, ?, ?, ?)",
                1, "FileProcessJob", JobStatus.STARTED.name(), JobType.FILE_READING.name(), new Date());
    }

    @Test
    public void registerJobAndProcessFile_simpeFile_ok() throws FileNotFoundException, IOException, Exception {
        //arrange
        String fileName = "sections.xls";
        String path = this.getClass().getClassLoader().getResource("files/sections.xlsx").getFile();
        try (InputStream inputStream = new FileInputStream(path)) {
            MockMultipartFile file = new MockMultipartFile("sections", fileName,
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputStream);

            //act and expect
            mvc.perform(fileUpload("/register-job").file(file))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
        //It will avoid duplicate key exception! because, processExcelFile is running asynchronisely, multiple tests
        //will try to insert the same data of the same file at the same time! So, sleeping the thread does the trick!
        TimeUnit.SECONDS.sleep(3);

    }

    @Test
    public void findJobResult_existingJob_haveResult() throws Exception {
        //arrange
        Long jobId = 1L;

        //act and expect
        String result = mvc.perform(post("/job-result")
                .param("job-id", jobId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        JobResponseDTO expectedJob = JacksonMapperConfig.getObjectMapper().readValue(result, JobResponseDTO.class);

        //asserts
        Assert.assertNotNull(expectedJob);
        Assert.assertEquals(expectedJob.getStatus(), JobStatus.STARTED);
    }

    @Test
    public void findJobResult_nonExistingJob_notFound() throws Exception {
        //arrange
        Long jobId = Long.MAX_VALUE;

        //act and asserts
        mvc.perform(post("/job-result")
                .param("job-id", jobId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    //Write more and more tests for /register-job...
}
