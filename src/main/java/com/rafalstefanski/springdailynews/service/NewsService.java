package com.rafalstefanski.springdailynews.service;

import com.rafalstefanski.springdailynews.dto.NewsDto;

import java.util.List;

public interface NewsService {

    List<NewsDto> findAll();

    NewsDto getNewsById(long id);

    boolean isNewsUpdated(long id, String title, String url, String imgUrl, String description, String publishedAt);
}
