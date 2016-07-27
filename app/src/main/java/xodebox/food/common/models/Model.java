package xodebox.food.common.models;

import org.json.JSONObject;

import java.io.InputStream;

/**
 * A ready to use Model class with all possible constructors available.
 */
public class Model extends BaseModel {
    public Model() {
        super();
    }

    public Model(String inString) {
        super(inString);
    }

    public Model(JSONObject jsonObject) {
        super(jsonObject);
    }

    public Model(InputStream inputStream) {
        super(inputStream);
    }

}
