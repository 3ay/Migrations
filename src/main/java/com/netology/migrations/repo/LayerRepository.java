package com.netology.migrations.repo;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class LayerRepository {
    private final String script;
    private final String PATH = "select.sql";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LayerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.script = read(PATH);
    }

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getProductName(String customerName)
    {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", customerName);
        return namedParameterJdbcTemplate.queryForList(script, params, String.class);
    }
}
