package com.rafalstefanski.springdailynews.dao;

import com.rafalstefanski.springdailynews.dto.NewsDto;

import java.util.List;

public interface NewsDao {
    boolean isNewsUpdated(long id, String title, String url, String imgUrl, String description, String publishedAt);

    List<NewsDto> findAll();

    NewsDto getNewsById(long id);
}
