package com.kmv.geological.service;

import com.kmv.geological.BaseTest;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassFilterRequestDTO;
import com.kmv.geological.domain.dto.geologicalclass.GeologicalClassResponseDTO;
import com.kmv.geological.domain.dto.page.SimplePageResponseDTO;
import com.kmv.geological.service.api.GeologicalClassService;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class GeologicalClassServiceTest extends BaseTest {

    @Autowired
    private GeologicalClassService geologicalClassService;

    @Before
    public void init() {
        jdbcTemplate.update("insert into sections(id, name, created_at) values(?, ?, ?)", 1, "Section 1", new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                1, "geo1", "code1", 1, new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                2, "geo2", "code2", 1, new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                3, "anotherGeo", "anotherCode", 1, new Date());
    }

    @Test
    public void filter_simpleFilter_succeed() {
        //arrange
        int page = 0, size = 2;
        String name = "geo", code = "code";
        GeologicalClassFilterRequestDTO requestDTO = new GeologicalClassFilterRequestDTO(name, code, page, size);

        //act
        SimplePageResponseDTO<GeologicalClassResponseDTO> pageResponse = geologicalClassService.filter(requestDTO);

        //asserts
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(3, pageResponse.getCount());
        Assert.assertEquals(2, pageResponse.getContent().size());
    }

    @Test
    public void filter_simpleFilterWithNonExistingValues_empty() {
        //arrange
        int page = 0, size = 2;
        String name = "nonExisting";
        GeologicalClassFilterRequestDTO requestDTO = new GeologicalClassFilterRequestDTO(name, null, page, size);

        //act
        SimplePageResponseDTO<GeologicalClassResponseDTO> pageResponse = geologicalClassService.filter(requestDTO);

        //asserts
        Assert.assertNotNull(pageResponse);
        Assert.assertEquals(0, pageResponse.getCount());
        Assert.assertTrue(pageResponse.getContent().isEmpty());
    }
}
