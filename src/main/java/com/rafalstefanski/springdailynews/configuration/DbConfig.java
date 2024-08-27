package com.rafalstefanski.springdailynews.configuration;

import com.rafalstefanski.springdailynews.model.News;
import com.rafalstefanski.springdailynews.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class DbConfig {

    private final DataSource dataSource;
    private final NewsRepository newsRepository;

    @Autowired
    public DbConfig(DataSource dataSource, NewsRepository newsRepository) {
        this.dataSource = dataSource;
        this.newsRepository = newsRepository;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void newsDataBaseInit() {
        String sqlDrop = "DROP TABLE IF EXISTS news";
        getJdbcTemplate().update(sqlDrop);
        String sqlCreate = "CREATE TABLE IF NOT EXISTS news (id INT PRIMARY KeY AUTO_INCREMENT, " +
                "title VARCHAR (1000) NOT NULL, url VARCHAR (1000), img_url VARCHAR (1000), description VARCHAR (10000), published_at VARCHAR (250))";
        getJdbcTemplate().update(sqlCreate);

        String sqlInsert = "INSERT INTO news(title, url, img_url, description, published_at) VALUES (?, ?, ?, ?, ?)";
        Optional<News> newsOptional = newsRepository.getNewsFromApi();
        newsOptional.ifPresent(news -> news.getArticles()
                .forEach(article -> getJdbcTemplate().update(sqlInsert,
                        article.getTitle(),
                        article.getUrl(),
                        article.getUrlToImage(),
                        article.getDescription(),
                        article.getPublishedAt())));
    }
}
