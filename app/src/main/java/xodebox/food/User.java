package xodebox.food;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Created by shath on 6/5/2016.
 * Java class for user data model.
 *
 */

public class User {


    private String email;
    private String login_id;
    private String name;


    private Uri photoUri;
    private Bitmap photo;
    private Drawable drawablePhoto;

    public User(String login_id, String name, String email)
    {
        setName(name);
        setEmail(email);
        setLogin_id(login_id);
    }

    /** Attribute Accessor methods (AKA Getters and setters ) **/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogin_id() {
        return login_id;
    }
    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public Uri getPhotoUri() {return photoUri;}
    public void setPhotoUri(Uri photoUri) {this.photoUri = photoUri;}

    public Bitmap getPhoto() {return photo;}
    public void setPhoto(Bitmap photo) {this.photo = photo;}

    public Drawable getDrawable() {return drawablePhoto;}

    public void setDrawable(Drawable drawablePhoto) {this.drawablePhoto = drawablePhoto;}
}
