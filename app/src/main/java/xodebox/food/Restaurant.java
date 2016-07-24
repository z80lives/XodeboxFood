package xodebox.food;

/**
 * Created by Max on 24/7/2016.
 */
public class Restaurant {
    public Restaurant(int id, int manager, String address, String email, String website, String name) {
        this.id = id;
        this.manager = manager;
        this.address = address;
        this.email = email;
        this.website = website;
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    int id;
    int manager;
    String address,email,website,name;

}
