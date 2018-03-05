package com.kmv.geological.service.api;

import com.kmv.geological.domain.dto.job.JobResponseDTO;
import com.kmv.geological.domain.entity.JobEntity;
import java.io.InputStream;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface JobService {

    public JobEntity initJob(String name);

    public JobResponseDTO findJob(Long id);

    @Async
    public void processExcelFile(JobEntity jobEntity, InputStream inputStream);
}
