package com.rafalstefanski.springdailynews.dto;

public class NewsDto {

    private long id;
    private String title;
    private String url;
    private String imgUrl;
    private String description;
    private String publishedAt;

    public NewsDto() {
    }

    public NewsDto(long id, String title, String url, String imgUrl, String description, String publishedAt) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imgUrl = imgUrl;
        this.description = description;
        this.publishedAt = publishedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

}
