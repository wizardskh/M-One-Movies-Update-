package com.sawkyawhtin.clientapp;

public class LiveTvModel {
    String name,link,image;

    public LiveTvModel(String name, String link, String image) {
        this.name = name;
        this.link = link;
        this.image = image;
    }

    public LiveTvModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
