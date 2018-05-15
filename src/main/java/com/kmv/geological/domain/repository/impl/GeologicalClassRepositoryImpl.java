package com.kmv.geological.domain.repository.impl;

import com.kmv.geological.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.dto.page.SimplePageResponseDTO;
import com.kmv.geological.domain.entity.GeologicalClassEntity;
import com.kmv.geological.domain.repository.AbstractRepository;
import com.kmv.geological.domain.repository.custom.GeologicalClassRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 *
 * @author khosro.makari@gmail.com
 */
@Repository
public class GeologicalClassRepositoryImpl extends AbstractRepository implements GeologicalClassRepositoryCustom {

    @Override
    public SimplePageResponseDTO<GeologicalClassResponseDTO> filter(GeologicalClassFilterRequestDTO requestDTO) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<GeologicalClassResponseDTO> query = builder.createQuery(GeologicalClassResponseDTO.class);
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<GeologicalClassEntity> root = query.from(GeologicalClassEntity.class);
        final List<Predicate> predicates = new ArrayList<>(2);
        //something generic for predicators : https://stackoverflow.com/questions/11138118/really-dynamic-jpa-criteriabuilder
        //adding case-insensitive predicates
        if (requestDTO.getName() != null) {
            predicates.add(builder.like(
                    builder.lower(root.<String>get("name")), "%" + requestDTO.getName().toLowerCase() + "%"));
        }
        if (requestDTO.getCode() != null) {
            predicates.add(builder.like(
                    builder.lower(root.<String>get("code")), "%" + requestDTO.getCode() + "%"));
        }

        query.multiselect(root.get("id"), root.get("name"), root.get("code"));
        countQuery.select(builder.count(countQuery.from(GeologicalClassEntity.class)));

        if (predicates.size() > 0) {
            query.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
            countQuery.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        query.orderBy(builder.desc(root.get("id")));
        List<GeologicalClassResponseDTO> responseDTOs = getEntityManager().createQuery(query)
                .setFirstResult((requestDTO.getPage()) * requestDTO.getSize()).
                setMaxResults(requestDTO.getSize()).getResultList();
        Long count = getEntityManager().createQuery(countQuery).getSingleResult();
        SimplePageResponseDTO<GeologicalClassResponseDTO> response = new SimplePageResponseDTO(responseDTOs, count);
        return response;
    }

}
