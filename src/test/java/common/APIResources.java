package common;

import java.util.HashMap;

public enum APIResources {
    AddPlaceAPI("/maps/api/place/add/json"),
    UpdatePlaceAPI("/maps/api/place/update/json"),
    DeletePlaceAPI("/maps/api/place/delete/json"),
    GetPlaceAPI("/maps/api/place/get/json"),
    ExpectedContentType("application/json;charset=UTF-8"),
    AddPlaceAPIStatusCode(200),
    ContentTypeHeader("Content-Type","application/json"),
    KeyQueryParameter("key","qaclick123");

    private String resource;
    private int numericResource;
    private String hashMapKey;
    private String hashMapValue;
    private HashMap<String,String> hashMapResource = new HashMap<>();

    APIResources(String hashMapKey, String hashMapValue){
        hashMapResource.put(hashMapKey,hashMapValue);
    }

    APIResources(String resource){
        this.resource = resource;
    }

    APIResources(int numericResource){
        this.numericResource = numericResource;
    }

    public String getResource(){
        return resource;
    }

    public int getNumericResource(){
        return numericResource;
    }

    public HashMap<String,String> getHashMapResource(){
        return hashMapResource;
    }

}
