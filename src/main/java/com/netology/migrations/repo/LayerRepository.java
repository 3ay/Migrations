package com.netology.migrations.repo;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
@AllArgsConstructor
public class LayerRepository {
    private final String PATH = "select.sql";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static String read(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<List<Object>> getProductName(String customerName) {
        String sql = read(PATH);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", customerName);
        try {
            List<Object> result = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
                List<String>  list = new ArrayList<>();
                list.add(rs.getString("product_name"));
                return list;
            });
            return Optional.of(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
