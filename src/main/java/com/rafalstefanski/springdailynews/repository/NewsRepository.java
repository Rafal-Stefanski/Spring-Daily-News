package com.rafalstefanski.springdailynews.repository;

import com.rafalstefanski.springdailynews.model.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Repository
public class NewsRepository {

    @Value("${news-repository.newsApiKey}")
    private String newsApiKey;
    @Value("${news-repository.fromCountry}")
    private String fromCountry;

    public Optional<News> getNewsFromApi() {
        RestTemplate restTemplate4News = new RestTemplate();
        return Optional.ofNullable(restTemplate4News.getForObject("https://newsapi.org/v2/top-headlines?country=" + fromCountry + "&apiKey=" + newsApiKey,
                News.class));
    }

}
