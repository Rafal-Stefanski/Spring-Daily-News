package com.rafalstefanski.springdailynews.controller;

import com.rafalstefanski.springdailynews.repository.NewsDao;
import com.rafalstefanski.springdailynews.dto.NewsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsDao newsDao;

    public NewsController(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("newsList", newsDao.findAll());
        return "news-gui/news-view";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        Optional<NewsDto> newsById = Optional.ofNullable(newsDao.getNewsById(id));
        if (newsById.isPresent()) {
            model.addAttribute("news2Edit", newsById.get());
            return "news-gui/news-edit";
        }
        return "redirect:/news-view";
    }

    @PostMapping("/modify")
    public String modifyNews(long id, String title, String url, String imgUrl, String description, String publishedAt) {
        if (newsDao.isNewsUpdated(id, title, url, imgUrl, description, publishedAt)) {
            return "redirect:/news";
        }
        return "news-gui/news_404";
    }

}
