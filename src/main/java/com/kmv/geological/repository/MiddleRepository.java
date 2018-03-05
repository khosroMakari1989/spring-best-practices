package com.kmv.geological.repository;

import com.kmv.geological.domain.entity.BaseEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author khosro.makari@gmail.com
 */
@NoRepositoryBean
public interface MiddleRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

}
