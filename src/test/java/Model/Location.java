package Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Location {

    String postcode;
    String country;
    String countryabbreviation;
    ArrayList<Place> places;


    public String getPostcode() {

        return postcode;
    }

    public String getCountry() {

        return country;
    }

    public String getStateabbreviation() {

        return countryabbreviation;
    }

    public ArrayList<Place> getPlaces() {

        return places;
    }


    @JsonProperty("post code") // JSON PROPERTİN POST CODE İLE EŞLEŞTİR
    public void setPostcode(String postcode) {

        this.postcode = postcode;
    }


    public void setCountry(String country) {

        this.country = country;
    }

    @JsonProperty("country abbreviation")
    public void setCountryabbreviation(String countryabbreviation) {
        this.countryabbreviation = countryabbreviation;
    }


    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }
}












