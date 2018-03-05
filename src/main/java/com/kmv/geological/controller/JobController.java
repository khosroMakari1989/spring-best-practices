package com.kmv.geological.controller;

import com.kmv.geological.domain.dto.job.JobResponseDTO;
import com.kmv.geological.domain.entity.JobEntity;
import com.kmv.geological.service.api.JobService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author khosro.makari@gmail.com
 */
@RestController
public class JobController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobController.class.getName());

    @Autowired
    private JobService jobService;

    @PostMapping("/register-job")
    public Long registerJobAndProcessFile(@RequestParam(value = "sections") MultipartFile file) throws IOException {
        LOGGER.info("FILE NAME: ", file.getOriginalFilename());
        JobEntity jobEntity = jobService.initJob(file.getOriginalFilename());
        LOGGER.info("JOB ID: ", jobEntity.getId());
        jobService.processExcelFile(jobEntity, file.getInputStream());
        return jobEntity.getId();
    }

    @PostMapping("/job-result")
    public JobResponseDTO jobResutl(@RequestParam("job-id") Long jobId) {
        LOGGER.info("JOB ID: ", jobId);
        return jobService.findJob(jobId);
    }
}
