package telran.album.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.album.dao.Album;
import telran.album.dao.AlbumImpl;
import telran.album.model.Photo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    private Album album;
    private Photo[] photos;
    private final int capacity = 6;

    private final Comparator<Photo> comparator = ((p1, p2) -> {
        int res = Integer.compare(p1.getAlbumId(), p2.getAlbumId());
        return res != 0 ? res : Integer.compare(p1.getPhotoId(), p2.getPhotoId());
    });


    @BeforeEach
    void setUp() {
        album = new AlbumImpl(capacity);
        photos = new Photo[6];
        photos[0] = new Photo(1, 1, "First Photo", "http://example.com/photo1", LocalDate.of(2023, 1, 1));
        photos[1] = new Photo(1, 2, "Second Photo", "http://example.com/photo2", LocalDate.of(2023, 2, 1));
        photos[2] = new Photo(1, 3, "Third Photo", "http://example.com/photo3", LocalDate.of(2023, 3, 1));
        photos[3] = new Photo(2, 4, "Fourth Photo", "http://example.com/photo4", LocalDate.of(2023, 4, 1));
        photos[4] = new Photo(2, 5, "Fifth Photo", "http://example.com/photo5", LocalDate.of(2023, 5, 1));
        for (Photo p : photos) {
            album.addPhoto(p);
        }
    }

    @Test
    void testAddPhoto() {
        assertFalse(album.addPhoto(null));
        assertFalse(album.addPhoto(photos[2]));
        Photo photo_5 = new Photo(1, 6, "Six Photo", "http://example.com/photo6", LocalDate.of(2023, 6, 1));
        assertTrue(album.addPhoto(photo_5));
        assertEquals(capacity, album.size());
        Photo photo_6 = new Photo(1, 7, "Seven Photo", "http://example.com/photo7", LocalDate.of(2023, 7, 1));
        assertFalse(album.addPhoto(photo_6));
    }

    @Test
    void testRemovePhoto() {
        assertTrue(album.removePhoto(3, 1));
        assertFalse(album.removePhoto(9, 9));
        assertEquals(4, album.size());
    }

    @Test
    void testUpdatePhoto() {
        String newUrl = "http://example.com/photo666";
        assertTrue(album.updatePhoto(3, 1, newUrl));
        assertEquals(newUrl, album.getPhotoFromAlbum(3, 1).getUrl());
        assertFalse(album.updatePhoto(9, 9, "http://example.com/photo9"));
    }

    @Test
    void testGetPhotoFromAlbum() {
        assertEquals(photos[2], album.getPhotoFromAlbum(3, 1));
        assertNull(album.getPhotoFromAlbum(9, 9));


    }

    @Test
    void testGetAllPhotoFromAlbum() {
        Photo[] actual = album.getAllPhotoFromAlbum(2);
        Arrays.sort(actual, comparator);
        Photo[] expected = {photos[3], photos[4]};
        assertArrayEquals(expected, actual);

    }

    @Test
    void testGetPhotoBetweenDate() {
        Photo[] actual = album.getPhotoBetweenDate(LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 4, 1));
        Arrays.sort(actual, comparator);
        System.out.println(Arrays.toString(actual));
        Photo[] expected = {photos[0], photos[1], photos[2]};
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSize() {
        assertEquals(5, album.size());
        Photo photo_6 = new Photo(1, 7, "Seven Photo", "http://example.com/photo7", LocalDate.of(2023, 7, 1));
        album.addPhoto(photo_6);
        assertEquals(6, album.size());
    }
}