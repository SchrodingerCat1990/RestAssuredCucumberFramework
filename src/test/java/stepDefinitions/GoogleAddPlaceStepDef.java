package stepDefinitions;

import baseClasses.GoogleAPICreateJSON;
import common.APIResources;
import common.Utilities;
import common.pojoClasses.GoogleAPIPojo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(Cucumber.class)
public class GoogleAddPlaceStepDef extends Utilities {
    HashMap<String,String> headers = new HashMap<>();
    HashMap<String,String> queryParams = new HashMap<>();
    Response response = null;
    RequestSpecification requestSpecification = null;
    GoogleAPICreateJSON createJSON = new GoogleAPICreateJSON();
    static String place_id = "";
    static String scenarioResponse = "";
    JsonPath jsonPath = null;

    @Given("Request with properties file {string},headers {string},queryParameter {string} and body and {int},{string},{string},{string}," +
            "{string},{string},{string},{string},{string}")
    public void request_is_properties_file_headers_key_and_value_parameter_key_and_value_and_body(String propertiesFile, String ContentTypeHeader,
                                                                                                  String KeyQueryParameter, Integer accuracy, String name,
                                                                                                  String phone_number, String address, String website, String language,
                                                                                                  String types, String lat, String lng) {
        List<String> typeList = Arrays.asList(types.split(","));
        requestSpecification = given().log().all().spec(setRequestSpecification(propertiesFile,
                /*
                * valueOf in an enum class calls the constructor
                * */
                APIResources.valueOf(ContentTypeHeader).getHashMapResource(),
                APIResources.valueOf(KeyQueryParameter).getHashMapResource()))
                .body(createJSON.createAddPlaceJSON(accuracy,name,phone_number,address,website,language,typeList,lat,lng));
    }
    @When("resource {string} and http method {string}")
    public void resource_and_http_method(String resource, String httpMethod) {
        response = getResponse(requestSpecification,APIResources.valueOf(resource).getResource(),httpMethod);
    }

    /*
    * Implement generics so that POJO classes can be used for extraction
    * of response body
    * */
    /*@Then("Validate status code should be {string} and content {string} and extract place_id")
    public void validate_status_code_should_be_and_content_and_extract_place_id(String statusCode, String contentType) {
        GoogleAPIPojo.AddPlaceResponse finalResponse = response.then().spec(setResponseSpecification(APIResources.valueOf(statusCode).getNumericResource(),
                APIResources.valueOf(contentType).getResource())).extract().response().as(GoogleAPIPojo.AddPlaceResponse.class);

        place_id = finalResponse.getPlace_id();
        System.out.println("Place ID is: "+place_id);
    }*/

    @Then("Validate status code should be {string} and content {string}")
    public void validate_status_code_should_be_and_content(String statusCode, String contentType) {
        scenarioResponse = response.then().spec(setResponseSpecification(APIResources.valueOf(statusCode).getNumericResource(),
                APIResources.valueOf(contentType).getResource())).extract().response().asString();
    }

    /*
    * Add later to ensure both places are deleted by delete call
    * */
    /*@Then("Extract place_id")
    public void extract_place_id() {
        jsonPath = new JsonPath(scenarioResponse);
        place_ids.add(jsonPath.getString("place_id"));
        System.out.println("Place ID is: "+place_id);

    }*/

    @Then("Validate {string} and status code {string} is same as returned by resource {string} and method {string} with properties file {string}," +
            "headers {string},queryParameter {string}")
    public void validate_is_same_as_returned_by_with_headers_query_parameter(String expectedName,String statusCode, String resource, String httpMethod,
                                                                             String propertiesFile, String ContentTypeHeader,
                                                                             String KeyQueryParameter) {
        jsonPath = new JsonPath(scenarioResponse);
        place_id = jsonPath.getString("place_id");
        HashMap<String,String> queryParams = new HashMap<>();
        queryParams.put("place_id",place_id);
        requestSpecification = given().spec(setRequestSpecification(propertiesFile, APIResources.valueOf(ContentTypeHeader).getHashMapResource(),
                APIResources.valueOf(KeyQueryParameter).getHashMapResource(),queryParams)).body(createJSON.deletePlaceRequest(place_id));
        resource_and_http_method(httpMethod,resource);
        validate_status_code_should_be_and_content(statusCode,"ExpectedContentType");
        Assert.assertEquals(expectedName,new JsonPath(scenarioResponse).getString("name"));
    }

    @Given("Request with properties file {string},headers {string},queryParameter {string}")
    public void request_with_properties_file_headers_query_parameter(String propertiesFile, String ContentTypeHeader,
                                                                     String KeyQueryParameter) {
        requestSpecification = given().spec(setRequestSpecification(propertiesFile,APIResources.valueOf(ContentTypeHeader).getHashMapResource(),
                APIResources.valueOf(KeyQueryParameter).getHashMapResource())).body(createJSON.deletePlaceRequest(place_id));
    }
}
