package xodebox.food;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Max on 24/7/2016.
 * @deprecated I don't see us using this.
 */
public class Image {

    int restaurant;
    int type;
    int width;
    int height;
    int size;

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


    /**
     * show an image into a provided view
     * @param ctx
     * @param imgview
     */
    public void show(Context ctx, ImageView imgview){
        Picasso.with(ctx).setIndicatorsEnabled(true);
        Picasso.with(ctx).load(this.image_link).into(imgview);
    }


}
