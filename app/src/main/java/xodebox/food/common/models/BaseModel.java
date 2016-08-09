package xodebox.food.common.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//TODO Complete the javadocs.
//TODO Refactor
/**
 * Helper Class for the data model objects, to be read mainly by the UI objects.
 * Implement with the appropriate constructor.
 * @author Ibrahim Shath
 */
public abstract class BaseModel {
    private static final String TAG = "BaseModel";
    private Map<String, String> strProperties;
    private Map<String, BaseModel> childModels; //Create inheritence for the Models.
    private Map<String, String[]> childArrays; //Create map for arrays seperately

    /**
     * Construct an empty model.
     */
    public BaseModel() {
        strProperties = new HashMap<String, String>();
        childArrays   = new HashMap<>();
        childModels = new HashMap<>();
    }

    /**
     * Construct model from an xml or JSON String
     * Refer to the class documentation for the format.
     * @param inString String parameter can contain either JSON data object or XML data object.
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
     * Construct Base Model from json object
     * @param jsonObject A valid jsonObject
     * @deprecated Untested
     */
    public BaseModel(JSONObject jsonObject){
        this();
        try {
            //setAttributes(jsonObject);
            readJSONString(jsonObject.toString());
        }catch (Exception ex){
            Log.e(TAG, "BaseModel: "+ex.getMessage() );
        }
    }

    /**
     * Construct model from an xml or JSON InputStream
     * Refer to the class documentation for the format.
     * @param inputStream String parameter can contain either JSON data object or XML data object.
     */
    public BaseModel(InputStream inputStream)
    {
        this();
        //String inString = new InputSource(inputStream).toString();
        Scanner isScanner = new Scanner(inputStream, "UTF-16").useDelimiter("\\A");
        String inString = isScanner.hasNext() ? isScanner.next() : "";
        if (isStringXML(inString))
            readXMLString(inString);
        else
            readJSONString(inString);
        //Scanner isScanner = new Scanner(inputStream, "UTF-16").useDelimiter("\\A");
        //String buildString = isScanner.hasNext() ? isScanner.next() : "";
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

    /**
     * Intended for {@link ParcealableModel}
     * @return Associative array containing the local class attributes.
     */
    protected void setAttributeMap(Map<String, String> map){
        try {
            strProperties.putAll(map);
        }catch (Exception ex){
            Log.e(TAG, "setAttributeMap: ", ex );
        }
    }

    /**
     * Getter for Model attributes. Should be used by View classes.
     * @return {@code HashMap<String, String> Hashmap of all attributes, mapped from key to value.
     */
    public Map<String, String> getAttributes(){
        return strProperties;
    }

    /**
     * Add new attribute to the object.
     * @param key String value for the key.
     * @param value String value for the attribute.
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
     * Please refer to the class documentation for more details.
     * @param buildString Must be an xml or a json string.
     * @param modelClass Should be subclass of {@link BaseModel}
     * @param <T> Should be same as modelClass
     * @return Array List of the models.
     */
    public static <T> ArrayList<T> buildArrayList(String buildString, Class<T> modelClass){
        if(isStringXML(buildString))
            return buildXMLArrayList(buildString, modelClass); //The string argument was xml
        else
            return buildJSONArrayList(buildString, modelClass); //Otherwise assume the string is in JSON format
    }

    public static <T> ArrayList<T> buildArrayList(InputStream inputStream, Class<T> modelClass){
        Scanner isScanner = new Scanner(inputStream, "UTF-16").useDelimiter("\\A");
        String buildString = isScanner.hasNext() ? isScanner.next() : "";
        return buildArrayList(buildString, modelClass);
    }

    /**
     * Build an arraylist of {@link #BaseModel()} out of xml string
     * @param buildString XML String
     * @param modelClass Should be subclass of {@link BaseModel}
     * @param <T> Should be same as modelClass
     * @return Array List of the models.
     */
    private static <T> ArrayList<T> buildXMLArrayList(String buildString, Class<T> modelClass)
    {
        ArrayList<T> arrayList = new ArrayList<>();

        final  String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-16\"?>\n";
        buildString = buildString.replace("\n", "").replace("\r","");
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
                if (node == null)       //Bug fix
                    continue;

                //Transform content of item tag into string format
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                StringWriter stringWriter = new StringWriter();
                transformer.transform(new DOMSource(node), new StreamResult(stringWriter));
                String xmlString = stringWriter.toString();

                //Create new object from it, and add the object into our return list
                T model;
                Constructor modelConstructor = modelClass.getConstructor(String.class);
                model = (T) modelConstructor.newInstance(xmlString);
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

    /**
     * Build an arraylist of {@link #BaseModel()} out of json string
     * @param buildString
     * @param modelClass
     * @param <T>
     * @return
     */
    private static <T> ArrayList<T> buildJSONArrayList(String buildString, Class<T> modelClass)
    {
        ArrayList<T> arrayList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(buildString);
            JSONArray itemArray = jsonObject.getJSONArray(jsonObject.names().get(0).toString()); //First item should be labeled
            //for each item in the array
            for(int i=0; i<itemArray.length(); i++)
            {
                String itemString = itemArray.get(i).toString();

                //Create an instance of the model object
                T model;
                Constructor<T> modelConstructor = modelClass.getConstructor(String.class);
                model = modelConstructor.newInstance(itemString);
                //Add the model object to the list
                arrayList.add(model);
            }
            jsonObject.toString();
        }catch (Exception ex)
        {
            logError(ex.getMessage());
        }
        return arrayList;
    }




    ///////////////////////////////////////////////////////////////////////////
    // Internal Methods
    ///////////////////////////////////////////////////////////////////////////
    private void readXMLString(String xmlString){
        InputSource inputSource = new InputSource(new StringReader(xmlString));
        readXMLInputSource(inputSource);
    }

    private void readXMLString(InputStream xmlStream)
    {
        InputSource inputSource = new InputSource(xmlStream);
        readXMLInputSource(inputSource);
    }

    /**
     * Parse XML from Input source and add attributes to the model according to our format.
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
     * @param inputSource
     */
    private void readXMLInputSource(InputSource inputSource)
    {
        //Read the string as an xml file
        //InputSource inputSource = new InputSource(new StringReader(xmlString));
        DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
            Document doc = xmlBuilder.parse(inputSource);
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
     * @see #jsonToMap(JSONObject)
     */
    private void readJSONString(String inString){
        try{
            JSONObject jObject = new JSONObject(inString);
            strProperties.putAll(jsonToMap(jObject));
        }catch (RuntimeException ex){
            Log.e(TAG, "readJSONString: ", ex);
            if(isTestLogger())
                System.out.println("Run time Exception occured: "+ex.getMessage());
        }
        catch (Exception ex)
        {
            if(isTestLogger())
                System.out.println("Exception occured: "+ex.getMessage());
            Log.e(TAG, "readJSONString: ", ex );
        }
    }

    /**
     * JSON to HashMap function, created for {@link #readJSONString(String) }.
     * @param jObject A valid {@link JSONObject}.
     * @throws JSONException
     */
    private HashMap<String, String> jsonToMap(JSONObject jObject)  throws  JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        //JSONObject jObject = new JSONObject(strArg);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ) {
            String key = (String) keys.next();

                //String value = jObject.getString(key);
               // map.put(key, value);
            Object obj = jObject.get(key);
            if(obj instanceof JSONObject) {
                childModels.put(key, new Model((JSONObject) obj));
            }
            else if(obj instanceof JSONArray) {
                try {
                    JSONArray values = (JSONArray) obj;     //Type casting here seems justified.
                    ArrayList<String> stringArray = new ArrayList<String>();
                    for (int i = 0; i < values.length(); i++) {
                        Object object = values.get(i);

                        if (!(object instanceof JSONObject) && !(object instanceof JSONArray)) {      //TODO: Do something with the child arrays and child objects
                            String strValue = (String) object;
                            stringArray.add(strValue);
                        }
                    }
                    String[] strArray = new String[stringArray.size()];
                    stringArray.toArray(strArray);
                    childArrays.put(key, strArray);
                }catch (Exception ex){
                    Log.e(TAG, "jsonToMap: Error processing JSON Array. ",ex );
                }
            }
            else {
                String value = jObject.getString(key);
                map.put(key, value);
            }

           /* if(jsonValue instanceof JSONArray)
            {
                JSONArray values = (JSONArray) jsonValue;     //Type casting here seems justified.
                ArrayList<String> stringArray = new ArrayList<>();
                for(int i=0; i<values.length(); i++){
                    Object object = values.get(i);

                    if( !(object instanceof JSONObject) && !(object instanceof JSONArray) ) {      //TODO: Do something with the child arrays and child objects
                        String strValue = (String) object;
                        stringArray.add(strValue);
                    }
                }
                childArrays.put(key, (String []) stringArray.toArray());

            }else if (jsonValue instanceof JSONObject)
            {
                JSONObject childObject = (JSONObject) jsonValue;

                Model childModel = new Model(childObject);
                childModels.put(key, childModel);
            }else {*/

            //}

        }

        return map;

    }

    /**
     * Tests whether given string is an xml string.
     * @param testString
     * @return {@code true} if the string may be valid xml.
     */
    private static boolean isStringXML(String testString){
        //We are going to do a simple check for now.
        if(testString.startsWith("<"))
            return true;
        return false;
    }



    ///////////////////////////////////////////////////////////////////////////
    // Internal Error Tracking
    ///////////////////////////////////////////////////////////////////////////
    private static boolean STD_ERR = false;         //Output error messages to std_err
    public static void enableDebugMessages(boolean val){STD_ERR = val;}
    private static boolean isTestLogger(){ return STD_ERR; }
    private static void logError(String errMsg){
        if(isTestLogger())
            System.err.println("BaseModel: "+ errMsg);
    }




    ///////////////////////////////////////////////////////////////////////////
    // Unused code.
    ///////////////////////////////////////////////////////////////////////////
    /**
     * //TODO: REMOVE THIS METHOD. 9:14 AM 7/15/2016, UNUSED. Replaced in favour of {@link #readJSONString(String)}
     * Add attributes using JSON data
     * Might throw exception, if the JSONObject is not well defined.
     * @return True on success <br /> False on failure
     * @deprecated In favour of {@link #readJSONString(String)}
     */
    public boolean setAttributes(JSONObject jsonObject) throws JSONException{
        Iterator<String> iKeys = jsonObject.keys();

        while (iKeys.hasNext())
        {
            String key = iKeys.next();
            String value = (String) jsonObject.get(key);        //Type casting here is probably a bad idea. // FIXME: 7/11/2016
            Object valueObject = jsonObject.get(key);
            // We do not accept child object or an array inside our JSON object.
            // if (value instanceof JSONArray || value instanceof JSONObject)
            //JSONObject childObject = jsonObject.getJSONObject(key);

            //     return false;
            if(valueObject instanceof JSONArray) {
                JSONArray values = (JSONArray) valueObject;     //Type casting here seems justified.
                ArrayList<String> stringArray = new ArrayList<>();
                for(int i=0; i<values.length(); i++){
                    Object object = values.get(i);

                    if( !(object instanceof JSONObject) && !(object instanceof JSONArray) ) {      //TODO: Do something with the child arrays and child objects
                        String strValue = (String) object;
                        stringArray.add(strValue);
                    }
                }
                childArrays.put(key, (String [])stringArray.toArray());

            } else if(valueObject instanceof JSONObject){
                JSONObject childObject = (JSONObject) valueObject;

                Model childModel = new Model(childObject);
                childModels.put(key, childModel);
            }else
                addProperty(key, value);
        }

        return true;
    }


    public Map<String, String[]> getChildArrays(){
        return childArrays;
    }

    public Map<String, BaseModel> getChildModels(){
        return childModels;
    }

    public String[] getChildArray(String key){
        return childArrays.get(key);
    }

    public BaseModel getChildModel(String key){
        return childModels.get(key);
    }

    /**
     * Copies specific keys from another Model into this Model.
     */
    public void copyKeyAttributes(String[] keys, Model srcModel){
        for (String key: keys){
            if(srcModel.getAttributes().containsKey(key)) {
                String value = srcModel.getProperty(key);
                strProperties.put(key, value);
            }else if(srcModel.getChildArrays().containsKey(key)){
                String[] value = srcModel.getChildArray(key);
                childArrays.put(key, value);
            }else if(srcModel.getChildModels().containsKey(key)){
                BaseModel value = srcModel.getChildModel(key);
                childModels.put(key, value);
            }
        }
    }
}
