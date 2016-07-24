package xodebox.food;

/**
 * Created by Max on 24/7/2016.
 */
public class Image {
    public Image(String image_link, int id, int type, int restaurant) {
        this.image_link = image_link;
        this.id = id;
        this.type = type;
        this.restaurant = restaurant;
    }

    String image_link;
    int id;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    int restaurant;
    int type;
    int width;
    int height;
    int size;

}
