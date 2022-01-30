package common;

import baseClasses.GoogleAPICreateJSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import common.pojoClasses.GoogleAPIPojo;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Assert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static io.restassured.RestAssured.given;

public class Utilities {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    GoogleAPICreateJSON createJSON = new GoogleAPICreateJSON();
    PrintStream stream = null;
    static FileOutputStream logFile = null;

    public Utilities(){
        try {
            if(logFile==null) {
                logFile = new FileOutputStream("APILogs.txt");
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Log file not found: "+e);
        }
    }

    public RequestSpecification setRequestSpecification(String propertiesFile, HashMap<String,String> headers, HashMap<String,String> queryParams){
            stream = new PrintStream(logFile);
            requestSpecification = new RequestSpecBuilder()
                    //attach logging with the request and response Specification object
                    .addFilter(RequestLoggingFilter.logRequestTo(stream))
                    .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                    .setBaseUri(loadProperties(propertiesFile,"BaseURI")).addHeaders(headers).addQueryParams(queryParams).build();
        return requestSpecification;
    }

    public RequestSpecification setRequestSpecification(String propertiesFile, HashMap<String,String> headers, HashMap<String,String> queryParams1,
                                                        HashMap<String,String> queryParams2){
        stream = new PrintStream(logFile);
        queryParams1.putAll(queryParams2);
        requestSpecification = new RequestSpecBuilder()
                //attach logging with the request and response Specification object
                .addFilter(RequestLoggingFilter.logRequestTo(stream))
                .addFilter(ResponseLoggingFilter.logResponseTo(stream))
                .setBaseUri(loadProperties(propertiesFile,"BaseURI")).addHeaders(headers).addQueryParams(queryParams1).build();
        return requestSpecification;
    }

    public ResponseSpecification setResponseSpecification(Integer statusCode,String contentType){
        responseSpecification =new ResponseSpecBuilder().expectStatusCode(statusCode).expectContentType(contentType).build();
        return responseSpecification;
    }

    /*public <T extends GoogleAPIPojo> T getGoogleAPIRequestType(String action, String place_id){
        GoogleAPIPojo.AddPlaceRequest addPlaceRequest = new GoogleAPIPojo.AddPlaceRequest();
        switch (action){
            case "AddPlace": {
                addPlaceRequest = createJSON.createAddPlaceJSON();
                return (T)() createJSON.createAddPlaceJSON();
                break;
            }
            case "UpdatePlace":{
                createJSON.updatePlaceJSON(place_id);
                break;
            }
        }
        //return createJSON;
    }*/

    public Response getResponse(RequestSpecification requestSpecification,String resource, String httpMethod){
        Response response = null;
        switch (httpMethod){
            case "POST":
            {
                response = requestSpecification.when().post(resource);
                break;
            }
            case "GET":
            {
                response = requestSpecification.when().get(resource);
                break;
            }
            case "PUT":
            {
                response = requestSpecification.when().put(resource);
                break;
            }
            case "DELETE":
            {
                response = requestSpecification.when().delete(resource);
                break;
            }
        }
        return response;
    }

    public String loadProperties(String fileName, String key){
        Properties globalProperties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
                    "/src/test/java/resources/" + fileName + ".properties");
            globalProperties.load(fis);
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){

        }
        return globalProperties.getProperty(key);
    }

    public static Map<String,Object> loadJSONToMap(final String jsonFile){
        Map<String,Object> map = null;

        final GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        final Gson gson = builder.create();

        try{
            final String jsonString = new String(Files.readAllBytes(Paths.get(jsonFile)));
            map = gson.fromJson(jsonString,Map.class);
        }
        catch (final JsonSyntaxException e){
            System.out.println("JsonSyntaxException "+ e);
        }
        catch (final JsonIOException e){
            System.out.println("JsonIOException "+ e);
        }
        catch (FileNotFoundException e){
            System.out.println("FileNotFoundException "+ e);
        }
        catch (IOException e){
            System.out.println("IOException "+ e);
        }
        Assert.assertEquals("file should be loaded" + jsonFile, true, !map.isEmpty());
        return map;
    }

    public static Object[][] toArrayOfArrays(final List outerList){
        final List<Object[]> resList = new ArrayList<>();
        for(final Object inner: outerList){
            final List innerList = (List) inner;
            resList.add(innerList.toArray());
        }
        return resList.toArray(new Object[resList.size()][]);
    }
}
