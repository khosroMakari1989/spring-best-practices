package com.kmv.geological.repository.api;

import com.kmv.geological.domain.entity.SectionEntity;
import com.kmv.geological.repository.MiddleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface SectionRepository extends MiddleRepository<SectionEntity, Long> {

    public Page<SectionEntity> findByJobId(Long jobId, Pageable pageable);
}
