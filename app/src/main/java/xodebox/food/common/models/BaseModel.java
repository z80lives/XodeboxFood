package xodebox.food.common.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Helper Class for the data model objects, to be read mainly by the UI objects.
 * Created by shath on 7/9/2016.
 */
public abstract class BaseModel {
    private static final String TAG = "BaseModel";
    private Map<String, String> strProperties;

    /**
     * Constructs a new empty model.
     */
    public BaseModel() {
        strProperties = new HashMap<String, String>();
    }

    /**
     * Construct model from an xml or JSON String
     * @param inString
     */
    public BaseModel(String inString)
    {
        this();
        if (isStringXML(inString))
            readXMLString(inString);
        else
            readJSONString(inString);
    }

    /**
     * Store all attribute into one array and return them
     * @return
     */
    public ArrayList<String> getAttributesList(){
        ArrayList<String> ret = new ArrayList<String>();
        ret.addAll(strProperties.values());
        return  ret;
    }

    public Map<String, String> getAttributes(){
        return strProperties;
    }

    /**
     * Add new attribute to the object.
     * @param key
     * @param value
     */
    public void addProperty(String key, String value){
        strProperties.put(key, value);
    }

    /**
     * Get the property mapped to the given key.
     * @param key Key string
     * @return Returns property if it exists, otherwise returns {@code null}.
     */
    public String getProperty(String key)
    {
        return strProperties.get(key);
    }


    /**
     * Add attributes using JSON data
     * Might throw exception, if the JSONObject is not well defined.
     * TODO: Modify the code so that we can accept JSON objects with inherited objects and arrays.
     * @return True on success <br /> False on failure
     */
    public boolean setAttributes(JSONObject jsonObject) throws JSONException{
        Iterator<String> iKeys = jsonObject.keys();

        while (iKeys.hasNext())
        {
            String key = iKeys.next();
            String value = (String) jsonObject.get(key);        //Type casting here is probably a bad idea. // FIXME: 7/11/2016 

            // We do not accept child object or an array inside our JSON object.
            // if (value instanceof JSONArray || value instanceof JSONObject)
           //     return false;

            addProperty(key, value);
        }

        return true;
    }

    /**
     * Copies attributes from another model. Overwrites existing attributes.
     * @param inModel
     * @return
     */
    public boolean copyAttributes(BaseModel inModel)
    {
        HashMap<String, String> srcHashMap = (HashMap<String, String>) inModel.getAttributes();
        try {
            strProperties.putAll(srcHashMap);
        }catch (Exception ex){
            Log.e(TAG, "setAttributes: "+ex.getMessage() );
            return false;
        }
        return true;
    }

    /**
     * Create an array list of this object.
     * @param buildString Must be an xml or a json string.
     * @return Array List of the models.
     */
    public static <T> ArrayList<T> buildArrayList(String buildString, Class<T> modelClass){
        ArrayList<T> arrayList = new ArrayList<>();
        final  String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n";

        try {
            //Prepare xml builder
            DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
            Document doc = xmlBuilder.parse(new InputSource(new StringReader(buildString)));
            Element root = doc.getDocumentElement();

            NodeList items = root.getElementsByTagName("item");

            //For each <item> tag
            for (int i = 0; i < items.getLength(); i++) {
                NodeList attrNodes = items.item(i).getChildNodes();
                Node node = items.item(i);

                //Write all child content into the serializer and build a string from it
                StringBuilder stringBuilder = new StringBuilder();
                LSSerializer lsSerializer = ((DOMImplementationLS) node.getOwnerDocument()
                        .getImplementation().getFeature("LS", "3.0")).createLSSerializer();
                lsSerializer.getDomConfig().setParameter("xml-declaration", false);

                for (int j = 0; j < attrNodes.getLength(); j++) {
                    stringBuilder.append(lsSerializer.writeToString(attrNodes.item(j)));
                }
                String xmlString = xmlHead + "<item>" + stringBuilder.toString() + "</item>";

                //modelClass.newInstance();
                T model;
                //T model = modelClass.getConstructor(); //(T) new BaseModel(xmlString) {};
                //Class clazz = Class.forName(modelClass.getName());
                Constructor modelConstructor = modelClass.getConstructor(String.class);
                model = (T) modelConstructor.newInstance(xmlString);
                //modelConstructor.newInstance(xmlString);
                arrayList.add((T) model);

            }
        }catch(NoSuchMethodException ex){
            if( isTestLogger() ) {
                System.err.println("Model Construction Failed: " + ex.toString());
                System.err.println("Please ensure the child model implements necessary constructors");
            }
        }catch (Exception ex)
        {
            if(isTestLogger()) {
                //Cannot parse xml file or construction failed
                System.err.println("Failed: " + ex.toString());
            }
        };

        return arrayList;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Private Methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Parse XML string and add attributes to the model according to our format.
     * Accepted format should look like this:
     * <pre>
     *  <?xml version="1.0" encoding="UTF-16" ?>
     *      {@code
     *      <item>
     *          <attr_name>Value</attr_name>
     *          <attr_name>Value</attr_name>
     *          ....
     *      </item>
     *      }
     * </pre>
     * @param xmlString
     */
    private void readXMLString(String xmlString)
    {
        //Read the string as an xml file
        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
            Document doc = xmlBuilder.parse(new InputSource(new StringReader(xmlString)));
            Element root = doc.getDocumentElement();
            NodeList level1Nodes = root.getChildNodes();

            for (int itemIndex = 0; itemIndex <= level1Nodes.getLength(); itemIndex++) {
                Node attr =  level1Nodes.item(itemIndex);
                //Skip if attribute node is null or not a valid xml element
                if (attr == null || attr.getNodeType() != Node.ELEMENT_NODE)  //bug fix
                    continue;
                //otherwise add the node to our local Hashmap
                String key = attr.getNodeName();
                String value = attr.getTextContent();       //Throws DOMException
                addProperty(key, value);
            }
        }catch (ParserConfigurationException ex)
        {
            if(isTestLogger())
                System.err.println("Parser configuration error!");
        }catch (Exception ex)
        {
            //Log.e(TAG, "BaseModel: "+ "Construction failed!");
            if(isTestLogger())
                System.err.println("Model Constructor: Error occured: "+ ex.getMessage());
        }
    }

    /**
     * Attempt to parse JSON string, and store the attributes to our model.
     * JSON object should look like this:
     * <pre>
     *     {@code { "attr1": "value1"
     *         "attr2": "value2"
     *          ....
     *       }
     *     }
     * </pre>
     * @param inString String containing JSON object. Preferably not formatted.
     */
    private void readJSONString(String inString){

    }

    /**
     * Tests whether given string is an xml string.
     * @param testString
     * @return {@code true} if the string may be valid xml.
     */
    private boolean isStringXML(String testString){
        //We are going to do a simple check for now.
        if(testString.startsWith("<"))
            return true;
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal Error Tracking
    ///////////////////////////////////////////////////////////////////////////
    private static boolean STD_ERR = false;         //Output error messages to std_err
    private static void enableDebugMessages(boolean val){STD_ERR = val;}
    private static boolean isTestLogger(){ return STD_ERR; }
}
