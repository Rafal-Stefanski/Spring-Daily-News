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

    private News[] getNews() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject("https://newsapi.org/v2/top-headlines?country=pl&apiKey=" + newsApiKey,
                        News[].class);
    }

    public Optional<News> getNewsFromApi() {
        RestTemplate restTemplate4News = new RestTemplate();
        return Optional.ofNullable(restTemplate4News.getForObject("https://newsapi.org/v2/top-headlines?country=pl&apiKey=" + newsApiKey,
                News.class));
//        try {
//        } catch (RestClientException exception) {
//            throw new RuntimeException(exception);
//        }
    }


}
