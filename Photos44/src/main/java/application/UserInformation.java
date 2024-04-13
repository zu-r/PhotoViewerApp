package application;

import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class UserInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<User> usersList;
    private final static String usersFile = "userinfo.ser";

    public UserInformation() {
        usersList = new ArrayList<User>();
        User stock = new User("stock");
        usersList.add(stock);
        stock.addAlbum("stock");
        Album stockAlbum = stock.getAlbum("stock");
        InputStream stream1 = null;
        InputStream stream2 = null;
        InputStream stream3 = null;
        InputStream stream4 = null;
        InputStream stream5 = null;
        try {
            stream1 = new FileInputStream("data/1.jpg");
            stream2 = new FileInputStream("data/2.jpg");
            stream3 = new FileInputStream("data/3.jpg");
            stream4 = new FileInputStream("data/4.jpg");
            stream5 = new FileInputStream("data/5.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Image image1 = new Image(stream1);
        Image image2 = new Image(stream2);
        Image image3 = new Image(stream3);
        Image image4 = new Image(stream4);
        Image image5 = new Image(stream5);
        stockAlbum.addPhoto(new Photo(image1));
        stockAlbum.addPhoto(new Photo(image2));
        stockAlbum.addPhoto(new Photo(image3));
        stockAlbum.addPhoto(new Photo(image4));
        stockAlbum.addPhoto(new Photo(image5));
        store(this);

    }

    public User getUser(String username) {
        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<User> getUserList() {
        return usersList;
    }

    public ArrayList<String> getUsernames() {
        ArrayList<User> users = getUserList();
        ArrayList<String> usernames = new ArrayList<String>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    public void addUser(String username) {
        if (usersList.isEmpty()) {
            usersList.add(new User(username));
            return;
        }

        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                return;
            }
        }

        usersList.add(new User(username));
    }

    public void delete(String username) {
        for (int i = usersList.size() - 1; i >= 0; i--) {
            if (usersList.get(i).getUsername().equals(username)) {
                usersList.remove(i);
                break;
            }
        }
    }

    public static void store(UserInformation userInfo) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile, false));
            oos.writeObject(userInfo);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static UserInformation load() {
        UserInformation userInfo = null;
        try {
            FileInputStream filestream = new FileInputStream(usersFile);
            ObjectInputStream ois = new ObjectInputStream(filestream);
            Object object = ois.readObject();
            userInfo = (UserInformation) object;
            ois.close();
        } catch (Exception e) {
            userInfo = new UserInformation();
            store(userInfo);
        }
        return userInfo;
    }
}
