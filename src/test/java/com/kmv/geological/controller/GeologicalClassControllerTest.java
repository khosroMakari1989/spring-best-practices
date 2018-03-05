package com.kmv.geological.controller;

import com.kmv.geological.BaseTest;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author khosro.makari@gmail.com
 */
public class GeologicalClassControllerTest extends BaseTest {

    @Before
    public void init() {
        jdbcTemplate.update("insert into sections(id, name, created_at) values(?, ?, ?)", 1, "Section 1", new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                1, "geo class 1", "code1", 1, new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                2, "geo2", "code2", 1, new Date());
        jdbcTemplate.update("insert into geologicalclasses(id, name, code, section_id, created_at) values(?, ?, ?, ?, ?)",
                3, "anotherGeo", "anotherCode", 1, new Date());
    }

    @Test
    public void filter_simpleFilter_ok() throws Exception {
        //arrange
        String name = "geo class";

        //act and asserts
        mvc.perform(get("/geologicals/filter")
                .contentType(MediaType.TEXT_PLAIN)
                .accept(MediaType.APPLICATION_JSON)
                .param("name", name))
                .andExpect(status().isOk())
                .andReturn();

    }

}
