package ru.alexpshkov.reportplugin.discord.objects;

public class Image {
    private final String url;

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
