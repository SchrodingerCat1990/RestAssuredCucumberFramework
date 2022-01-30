package baseClasses;

import common.pojoClasses.GoogleAPIPojo;

import java.util.ArrayList;
import java.util.List;

public class GoogleAPICreateJSON {
        GoogleAPIPojo.AddPlaceRequest googleAddAPIPojo = new GoogleAPIPojo.AddPlaceRequest();
        GoogleAPIPojo.UpdatePlaceDetailsRequest updatePlaceDetailsRequest = new GoogleAPIPojo.UpdatePlaceDetailsRequest();
        GoogleAPIPojo.DeletePlaceRequest deletePlaceRequest = new GoogleAPIPojo.DeletePlaceRequest();

        public GoogleAPIPojo.AddPlaceRequest createAddPlaceJSON(int accuracy,String name,String phone_number,String address,
        String website, String language,List<String> types, String lat, String lng){
                googleAddAPIPojo.setAccuracy(accuracy);
                googleAddAPIPojo.setName(name);
                googleAddAPIPojo.setPhone_number(phone_number);
                googleAddAPIPojo.setAddress(address);
                googleAddAPIPojo.setWebsite(website);
                googleAddAPIPojo.setLanguage(language);

                /*List<String> types = new ArrayList<>();
                types.add("shoe park");
                types.add("shop");
                types.add("test value");*/
                googleAddAPIPojo.setTypes(types);

                GoogleAPIPojo.AddPlaceRequest.Location location = new GoogleAPIPojo.AddPlaceRequest.Location();
                location.setLat(lat);
                location.setLng(lng);
                googleAddAPIPojo.setLocation(location);
                return googleAddAPIPojo;
        }

        public GoogleAPIPojo.UpdatePlaceDetailsRequest updatePlaceJSON(String place_id){
                updatePlaceDetailsRequest.setPlace_id(place_id);
                updatePlaceDetailsRequest.setAddress("70 Summer walk, USA");
                updatePlaceDetailsRequest.setKey("qaclick123");
                return updatePlaceDetailsRequest;
        }

        public GoogleAPIPojo.DeletePlaceRequest deletePlaceRequest(String place_id){
                deletePlaceRequest.setPlace_id(place_id);
                return deletePlaceRequest;
        }
}
