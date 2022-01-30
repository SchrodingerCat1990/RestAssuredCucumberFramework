Feature: Google add place validations

  @AddPlace @Regression
  Scenario Outline: Add a new place in google maps
    Given Request with properties file "globalProperties",headers "ContentTypeHeader",queryParameter "KeyQueryParameter" and body and <accuracy>,"<name>","<phone_number>","<address>","<website>","<language>","<types>","<lat>","<lng>"
    When resource "AddPlaceAPI" and http method "POST"
    Then Validate status code should be "AddPlaceAPIStatusCode" and content "ExpectedContentType"
#    And Extract place_id ##Add later to ensure both places are deleted
    And Validate "<name>" and status code "AddPlaceAPIStatusCode" is same as returned by resource "GET" and method "GetPlaceAPI" with properties file "globalProperties",headers "ContentTypeHeader",queryParameter "KeyQueryParameter"

    Examples:
      |accuracy|name           |phone_number      |address                  |website          |language |types                    |lat       |lng      |
      |50      |Frontline house|(+91) 983 893 3937|29, side layout, cohen 09|http://google.com|French-IN|shoe park,shop,test value|-38.383494|33.427362|
      |51      |Backline house |(+91) 983 893 3937|30, side layout, cohen 09|http://google.com|French-IN|shoe park,shop,test value|-38.383494|33.427362|

  @DeletePlace @Regression
  Scenario: Delete place in google maps
    Given Request with properties file "globalProperties",headers "ContentTypeHeader",queryParameter "KeyQueryParameter"
    When resource "DeletePlaceAPI" and http method "DELETE"
    Then Validate status code should be "AddPlaceAPIStatusCode" and content "ExpectedContentType"