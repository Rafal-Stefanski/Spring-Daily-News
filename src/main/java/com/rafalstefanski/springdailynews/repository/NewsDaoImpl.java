package com.rafalstefanski.springdailynews.repository;

import com.rafalstefanski.springdailynews.dto.NewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class NewsDaoImpl implements NewsDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NewsDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isNewsUpdated(long id, String title, String url, String imgUrl, String description, String publishedAt) {
        String sqlUpdate = "UPDATE news SET title = ?, url = ?, img_url = ?, description = ?, published_at = ? WHERE id = ?";
        NewsDto newsToChange = getNewsById(id);
        if (newsToChange != null) {
            jdbcTemplate.update(sqlUpdate, title, url, imgUrl, description, publishedAt, id);
            return true;
        }
        return false;
    }

    @Override
    public List<NewsDto> findAll() {
        List<NewsDto> newsList = new ArrayList<>();
        String sqlSelectAll = "SELECT * FROM news";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sqlSelectAll);
        maps.forEach(element -> {
            if (String.valueOf(element.get("description")).equals("null")) {
                element.put("description", "No description");
            }
            newsList.add(new NewsDto(
                    Long.parseLong(String.valueOf(element.get("id"))),
                    String.valueOf(element.get("title")),
                    String.valueOf(element.get("url")),
                    String.valueOf(element.get("img_url")),
                    String.valueOf(element.get("description")),
                    String.valueOf(element.get("published_at"))
            ));
        });
        return newsList;
    }

    @Override
    public NewsDto getNewsById(long id) {
        String sqlGetOne = "SELECT * FROM news WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlGetOne, (resultSet, i) -> new NewsDto(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("url"),
                resultSet.getString("img_url"),
                resultSet.getString("description"),
                resultSet.getString("published_at")), id);
    }
}
