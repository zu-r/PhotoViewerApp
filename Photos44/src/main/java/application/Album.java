package application;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.scene.image.Image;

public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Photo> photos;
    private Photo firstPhoto;
    private Photo latePhoto;
    private int index;

    public Album(String name) {
        this.name = name;
        photos = new ArrayList<>();
        firstPhoto = null;
        latePhoto = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo p) {
        photos.add(p);
    }

    public void removeIndex(int index) {
        photos.remove(index);
    }

    public Photo getPhoto(int index) {
        return photos.get(index);
    }

    public Image getImage(int index) {
        return photos.get(index).getImage();
    }

    public int getSize() {
        return photos.size();
    }

    public String getFirstDate() {
        if (firstPhoto != null) {
            return firstPhoto.getDate().getTime().toString();
        } else {
            return "";
        }
    }

    public String getLateDate() {
        if (latePhoto != null) {
            return latePhoto.getDate().getTime().toString();
        } else {
            return "";
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int i){
        index = i;
    }

    public void findDateRange() {
        if (photos.isEmpty()) {
            firstPhoto = null;
            latePhoto = null;
            return;
        }
        firstPhoto = photos.get(0);
        latePhoto = photos.get(0);
        Calendar firstDate = firstPhoto.getDate();
        Calendar lateDate = latePhoto.getDate();

        for (int i = 1; i < photos.size(); i++) {
            Photo currPhoto = photos.get(i);
            Calendar thisDate = currPhoto.getDate();
            if (thisDate.before(firstDate)) {
                firstDate = thisDate;
                firstPhoto = currPhoto;
            }
            if (thisDate.after(lateDate)) {
                lateDate = thisDate;
                latePhoto = currPhoto;
            }
        }
    }
}
