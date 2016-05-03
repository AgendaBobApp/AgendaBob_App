package casco.project1.dataBackend;

/**
 * Created by Baron on 5/2/2016.
 */

/**
 * place holder class for a User
 * not really sure what all needs to be in here
 */
public class User {
    private String name;
    private int imageIndex;

    User() {
        this.name = "William Olsen";
    }

    User(String name) {
        imageIndex = 1;
        this.name = name;
    }
    User(String name, int imageIndex) {
        this.imageIndex = imageIndex;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getImage() {
        return imageIndex;
    }
}
