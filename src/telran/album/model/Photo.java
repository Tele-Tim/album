package telran.album.model;

import telran.album.dao.Album;

import java.time.LocalDate;

public class Photo {
    private int albumId;
    private int photoId;
    private String title;
    private String url;
    private LocalDate date;

    public Photo(int albumId, int photoId, String title, String url, LocalDate date) {
        this.albumId = albumId;
        this.photoId = photoId;
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "albumId=" + albumId +
                ", photoId=" + photoId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo photo)) return false;

        return albumId == photo.albumId && photoId == photo.photoId;
    }

    @Override
    public int hashCode() {
        int result = albumId;
        result = 31 * result + photoId;
        return result;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public LocalDate getDate() {
        return date;
    }
}
