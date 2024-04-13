package application;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private ArrayList<Album> albumList;

    public User(String username) {
        this.username = username;
        albumList = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Album> getAlbums() {
        return albumList;
    }

    public ArrayList<String> getAlbumNames() {
        ArrayList<String> result = new ArrayList<>();
        for (Album album : albumList) {
            result.add(album.getName());
        }
        return result;
    }

    public Album getAlbum(String name) {
        for (Album album : albumList) {
            if (album.getName().equals(name))
                return album;
        }
        return null;
    }

    public void addAlbum(String name) {
        if (!albumExists(name)) {
            albumList.add(new Album(name));
        }
    }

    public void addAlbum(Album album) {
        if (!albumExists(album.getName())) {
            albumList.add(album);
        }
    }

    public void deleteAlbum(String album) {
        for (int i = albumList.size() - 1; i >= 0; i--) {
            if (albumList.get(i).getName().equals(album)) {
                albumList.remove(i);
            }
        }
    }

    public boolean albumExists(String name) {
        if (albumList.isEmpty()) {
            return false;
        }

        for (Album album : albumList) {
            if (album != null && album.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof User)) {
            return false;
        }
        User other = (User) o;
        return this.username.equals(other.getUsername());
    }
}
