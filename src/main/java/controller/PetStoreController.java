package controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import model.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by Rupali Gupta on 01/11/2021.
 * 
 */
public class PetStoreController {
	
	public static String BASE_URL = "https://petstore.swagger.io/v2";
    public static String PET_ENDPOINT = BASE_URL + "/pet";
    private RequestSpecification requestSpecification;

    public PetStoreController() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URL);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.registerParser("text/html", Parser.JSON);
        requestSpecification = requestSpecBuilder.build();
    }

    public List<Pet> getPetsByStatus(Status status) {
        return given(requestSpecification)
                .queryParam("status", Status.available.toString())
                .get(PET_ENDPOINT + "/findByStatus")
                .then().log().all()
                .extract().body()
                .jsonPath().getList("", Pet.class);
    }
    
    public Pet addPet(Pet pet) {
        return given(requestSpecification)
                .body(pet)
                .post(PET_ENDPOINT).as(Pet.class);
    }

    public Pet updatePet(Pet pet) {
        return given(requestSpecification)
                .body(pet)
                .put(PET_ENDPOINT).as(Pet.class);
    }

    public void deletePet(Pet pet) {
        given(requestSpecification)
                .pathParam("petId", pet.getId())
                .delete(PET_ENDPOINT + "/{petId}");
    }
    
    public void verifyPetDeleted(Pet pet) {
        given(requestSpecification)
               .pathParam("petId", pet.getId())
               .get(PET_ENDPOINT + "/{petId}")
               .then()
               .body(containsString("Pet not found"));
   }

   public Pet getPet(Pet pet) {
       return given(requestSpecification)
               .pathParam("petId", pet.getId())
               .get(PET_ENDPOINT + "/{petId}").as(Pet.class);
   }

}