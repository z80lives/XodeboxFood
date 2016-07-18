package xodebox.food;

//import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import xodebox.food.common.models.BaseModel;
import xodebox.food.common.models.Restaurant;

import static org.junit.Assert.assertNotNull;

/**
 * Run test on Model classes.
 * Created by shath on 7/13/2016.
 */
public class ModelTest {
    BaseModel testXMLModel;

    @Before
    public void initialize(){
        BaseModel.enableDebugMessages(true);
    }

    @Test
    public void testXML(){
        String testXml = "<?xml version=\"1.0\" encoding=\"UTF-16\" ?>"+
                "<item>" +
                "<name>Pizza Hut</name>" +
                "<description>Pizza Hut is an American restaurant chain and international franchise known for Italian-American" +
                "cuisine including pizza and pasta as well as side dishes and desserts.</description>" +
                "<image_url>http://paypizzapal.com/wp-content/uploads/2014/01/pizza-hut2.jpg</image_url>" +
                "</item>" ;

        testXMLModel = new BaseModel(testXml){
        };

        String name, description;

        name = testXMLModel.getProperty("name");
        description = testXMLModel.getProperty("description");

        System.out.println(testXMLModel.getAttributes().toString());

        assertNotNull(testXMLModel);
        assertNotNull(name);
        assertNotNull(description);

    }

    @Test
    public void testXMLModelList(){
        BaseModel xmlModel;
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-16\" ?>" +
                "<root>" +
                "<item>" +
                "<name>Pizza Hut</name>" +
                "<description>Pizza Hut is an American restaurant chain and international franchise known for Italian-American\n" +
                "            cuisine including pizza and pasta as well as side dishes and desserts.</description>" +
                "<image_url>http://paypizzapal.com/wp-content/uploads/2014/01/pizza-hut2.jpg</image_url>" +
                "</item>" +
                "<item>" +
                "<name>Olive Garden</name>" +
                "<description>Olive Garden, famed for their modern-day Italian dishes, has finally arrived in Malaysia. Their" +
                "traditional Italian specialties, made with only the freshest ingredients, are perfect for sharing with your loved ones." +
                "Indulge in a creamy bowl of fettucine alfredo, chow down on some hearty minestrone, or wash down your meal with a slice of" +
                "heavenly tiramisu. Embark on your very own Italian food journey here at Olive Garden.</description>" +
                "<image_url>https://farm1.staticflickr.com/396/19406875693_ee95ce95e6_o.jpg</image_url>\n" +
                "</item>" +
                "</root>";
        xmlModel = new BaseModel() {};
        ArrayList<Restaurant> xmlModelList = Restaurant.buildArrayList(xmlString, Restaurant.class);

        assertNotNull(xmlModel);
        System.out.println(xmlModelList.toString());

        for(Restaurant restaurant: xmlModelList) {
            assertNotNull(restaurant);
            assertNotNull( restaurant.getAttributes());
            System.out.println( restaurant.getAttributes());
        }
    }

    @Test
    public void testJSON(){
        String jsonString = "{\n" +
                "     \"name\" : \"Pizza Hut\",\n" +
                "     \"description\" : \"Pizza Hut is an American restaurant chain and international franchise known for Italian-American cuisine including pizza and pasta as well as side dishes and desserts.\",\n" +
                "     \"imageUrl\" : \"http://paypizzapal.com/wp-content/uploads/2014/01/pizza-hut2.jpg\"\n" +
                "   },\n";

        try {
            JSONObject jsonObject;
            testXMLModel = new Restaurant(jsonString);
            //jsonObject = new JSONObject(jsonString);
        }catch(Exception ex)
        {
            System.out.println("Error occured");
        }

        System.out.println(testXMLModel.getAttributes().toString());
        assertNotNull(testXMLModel);
    }

    @Test
    public void testJSONModelList(){
        String jsonString = "{\n" +
                " \"restaurant\" : [\n" +
                "   {\n" +
                "     \"name\" : \"Pizza Hut\",\n" +
                "     \"description\" : \"Pizza Hut is an American restaurant chain and international franchise known for Italian-American cuisine including pizza and pasta as well as side dishes and desserts.\",\n" +
                "     \"imageUrl\" : \"http://paypizzapal.com/wp-content/uploads/2014/01/pizza-hut2.jpg\"\n" +
                "   },\n" +
                "\n" +
                "   {\n" +
                "       \"name\" : \"Olive Garden\",\n" +
                "       \"description\" : \"Olive Garden, famed for their modern-day Italian dishes, has finally arrived in Malaysia. Their traditional Italian specialties, made with only the freshest ingredients, are perfect for sharing with your loved ones. Indulge in a creamy bowl of fettucine alfredo, chow down on some hearty minestrone, or wash down your meal with a slice on heavenly tiramisu. Embark on your very own Italian food journey here at Olive Garden.\",\n" +
                "       \"imageUrl\" : \"https://farm1.staticflickr.com/396/19406875693_ee95ce95e6_o.jpg\"\n" +
                "    }\n" +
                " ]\n" +
                "}";

        ArrayList<Restaurant> restaurants = Restaurant.buildArrayList(jsonString, Restaurant.class);

        System.out.println(restaurants.toString());
        assertNotNull(restaurants);


        for(Restaurant restaurant: restaurants) {
            assertNotNull(restaurant);
            assertNotNull( restaurant.getAttributes());
            System.out.println( restaurant.getAttributes());
        }
    }
}

