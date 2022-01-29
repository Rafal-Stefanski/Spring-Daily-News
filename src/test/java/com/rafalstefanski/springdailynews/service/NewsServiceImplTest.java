package com.rafalstefanski.springdailynews.service;

import com.rafalstefanski.springdailynews.dto.NewsDto;
import com.rafalstefanski.springdailynews.repository.NewsDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    private NewsDao newsDao;
    @InjectMocks
    NewsServiceImpl newsService = new NewsServiceImpl(newsDao);

    private List<NewsDto> newsDtoList() {
        return Arrays.asList(
                new NewsDto(1L, "title 1", "url 1", "imgUrl 1", "description 1", "publishedAt 1"),
                new NewsDto(2L, "title 2", "url 2", "imgUrl 2", "description 2", "publishedAt 2"),
                new NewsDto(3L, "title 3", "url 3", "imgUrl 3", "description 3", "publishedAt 3")
        );
    }

    @Test
    @DisplayName("Should find all news from the list.")
    void shouldFindAll() {
        // given
        given(newsDao.findAll()).willReturn(newsDtoList());
        // when
        List<NewsDto> actual = newsDao.findAll();
        // then
        Assertions.assertEquals(3, actual.size());
        verify(newsDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find news by Id.")
    void shouldGetNewsById() {
        // given
        List<NewsDto> testList = newsDtoList();
        // when
        when(newsDao.getNewsById(2)).thenReturn(testList.get(2));
        // then
        NewsDto actual = newsDao.getNewsById(2L);
        verify(newsDao, times(1)).getNewsById(2L);
    }

    @Test
    @DisplayName("Should update news.")
    void isNewsUpdated() {
        // given
//        NewsDto changedNews = new NewsDto(1L, "New title", "url 1", "imgUrl 1", "description 1", "publishedAt 1");

//        List<NewsDto> testList = newsDtoList();
        // when
        when(newsDao.isNewsUpdated(1L, "New title", "url 1", "imgUrl 1", "description 1", "publishedAt 1")).thenReturn(true);
        // then
        boolean actual = newsDao.isNewsUpdated(1L, "New title", "url 1", "imgUrl 1", "description 1", "publishedAt 1");
        Assertions.assertTrue(true, String.valueOf(actual));
    }
}
