package com.example.infocovid_proyecto.models;

public class Noticia {

    private String img;
    private String url;
    private String title;
    private String description;
    private String publishedAt;
    private String source;

    public Noticia(String img, String url, String title, String description, String publishedAt, String source) {
        this.img = img;
        this.url = url;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
