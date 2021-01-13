package myproject;

import controller.PetStoreController;
import model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

/**
 * Created by Rupali Gupta on 01/11/2021.
 * 
 */
public class PetStoreTest {
   PetStoreController petsController;
    Pet pet = new Pet.Builder()
            .withId(RandomStringUtils.randomNumeric(10))
            .withName("Rupali's Pet")
            .withPhotoUrls(Collections.singletonList("https://commons.wikimedia.org/wiki/File:American_Pitbull_001.jpg"))
            .withStatus(Status.available)
            .withTags(Collections.singletonList(new Tag(1, "american-pitbull")))
            .inCategory(new Category(1, "dogs")).build();

    @BeforeClass
    public void beforeClass() {
        petsController = new PetStoreController();
    }

    @Test(priority = 0)
    public void addNewPetAndVerify() {
        Pet petResponse = petsController.addPet(pet);
        Pet petResponse1 = petsController.getPet(petResponse);
        assertThat(petResponse1.getId(), is(samePropertyValuesAs(pet.getId())));      
    }   

    @Test(priority = 1)
    public void getAllAvailablePetsAndVerify() {
        List <Pet> response = petsController.getPetsByStatus(Status.available);
        Pet myPet = response.stream().filter(p->pet.getId().equals(p.getId())).findAny().orElse(null);
        assertThat(myPet.getId(), is(samePropertyValuesAs(pet.getId())));
    }
    
    @Test(priority = 2)
    public void updatePetAndVerify() {
        pet.setStatus(Status.sold);
        Pet petResponse = petsController.updatePet(pet);
        Pet petResponse1 = petsController.getPet(petResponse);
        assertThat(petResponse1.getStatus(), is(samePropertyValuesAs(pet.getStatus())));
    }

    @Test(priority = 3)
    public void deletePetAndVerify() {
        petsController.deletePet(pet);
        petsController.verifyPetDeleted(pet);
    }
    
   
}