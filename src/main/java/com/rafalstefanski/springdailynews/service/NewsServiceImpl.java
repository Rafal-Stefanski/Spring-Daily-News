package com.rafalstefanski.springdailynews.service;

import com.rafalstefanski.springdailynews.dto.NewsDto;
import com.rafalstefanski.springdailynews.repository.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsDao newsDao;

    @Override
    public List<NewsDto> findAll() {
        return newsDao.findAll();
    }

    @Autowired
    public NewsServiceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public boolean isNewsUpdated(long id, String title, String url, String imgUrl, String description, String publishedAt) {
        return newsDao.isNewsUpdated(id, title, url, imgUrl, description, publishedAt);
    }

    @Override
    public NewsDto getNewsById(long id) {
        return newsDao.getNewsById(id);
    }
}
