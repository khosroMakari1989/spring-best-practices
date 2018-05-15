package com.kmv.geological.domain.repository.api;

import com.kmv.geological.domain.entity.GeologicalClassEntity;
import com.kmv.geological.domain.repository.MiddleRepository;
import com.kmv.geological.domain.repository.custom.GeologicalClassRepositoryCustom;

/**
 *
 * @author khosro.makari@gmail.com
 */
public interface GeologicalClassRepository extends MiddleRepository<GeologicalClassEntity, Long>, GeologicalClassRepositoryCustom {

}
