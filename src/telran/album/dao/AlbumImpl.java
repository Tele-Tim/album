package telran.album.dao;

import telran.album.model.Photo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

public class AlbumImpl implements Album {

    private Photo[] photos;
    private int size;

    public AlbumImpl(int capacity) {
        photos = new Photo[capacity];
    }

    @Override
    public boolean addPhoto(Photo photo) {
        if (photo == null || size == photos.length || getPhotoFromAlbum(photo.getPhotoId(), photo.getAlbumId()) != null) {
            return false;
        }
        photos[size++] = photo;
        return true;
    }

    @Override
    public boolean removePhoto(int photoId, int albumId) {
        for (int i = 0; i < size; i++) {
            if (photos[i].getPhotoId() == photoId && photos[i].getAlbumId() == albumId) {
                System.arraycopy(photos, i + 1, photos, i, size - i - 1);
                photos[--size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePhoto(int photoId, int albumId, String url) {
        for (int i = 0; i < size; i++) {
            if (photos[i].getPhotoId() == photoId && photos[i].getAlbumId() == albumId) {
                photos[i].setUrl(url);
                return true;
            }
        }
        return false;
    }

    @Override
    public Photo getPhotoFromAlbum(int photoId, int albumId) {
        for (int i = 0; i < size; i++) {
            if (photos[i].getPhotoId() == photoId && photos[i].getAlbumId() == albumId) {
                return photos[i];
            }
        }
        return null;
    }

    @Override
    public Photo[] getAllPhotoFromAlbum(int albumId) {
        return findPhotoByPredicate(e -> e.getAlbumId() == albumId);
    }

    @Override
    public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        return findPhotoByPredicate(e ->
                (e.getDate().isAfter(dateFrom) || e.getDate().equals(dateFrom)) &&
                        (e.getDate().isBefore(dateTo)));
    }

    @Override
    public int size() {
        return size;
    }

    private Photo[] findPhotoByPredicate(Predicate<Photo> predicate) {
        int count = 0;
        Photo[] arrayPhotos = new Photo[size];
        for (int i = 0; i < size; i++) {
            if (predicate.test(photos[i])) {
                arrayPhotos[count++] = photos[i];
            }
        }
        return Arrays.copyOf(arrayPhotos, count);
    }

}
