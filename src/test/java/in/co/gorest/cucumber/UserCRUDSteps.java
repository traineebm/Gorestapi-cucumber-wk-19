package in.co.gorest.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import in.co.gorest.allusersinfo.UserSteps;
import in.co.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

public class UserCRUDSteps {
    static String name = "Monica Singh" + TestUtils.getRandomValue();
    static String email = "mon.singh34@200ce.com" + TestUtils.getRandomValue();
    static String gender = "female";
    static String status = "active";
    static int userId;
    static ValidatableResponse response;

    @Steps
    UserSteps userSteps;


    @When("^I send a POST request to the application using a valid payload$")
    public void iSendAPOSTRequestToTheApplicationUsingAValidPayload() {
        response = userSteps.createUser(name,email,gender,status).statusCode(201).log().all();
    }

    @Then("^I should be able to create a new user$")
    public void iShouldBeAbleToCreateANewUser() {
        userId = response.log().all().extract().path("id");
        System.out.println(userId);
    }

    @And("^I get a valid status code from the application$")
    public void iGetAValidStatusCodeFromTheApplication() {
        response.assertThat().statusCode(200);
    }

    @When("^I send a GET request to the application to read newly created user$")
    public void iSendAGETRequestToTheApplicationToReadNewlyCreatedUser() {
        response = userSteps.getUserById(userId).statusCode(200).log().all();
    }

    @And("^I verify if the newly created user details are correct in the records$")
    public void iVerifyIfTheNewlyCreatedUserDetailsAreCorrectInTheRecords() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByFirstName(name);
        Assert.assertThat(userMap, hasValue(name));
    }

    @When("^I send a PATCH request to the application using a valid payload$")
    public void iSendAPATCHRequestToTheApplicationUsingAValidPayload() {
        name = "Dexter Maniac Monster";
        email="laboratory23@ace.com" + TestUtils.getRandomValue();
        userId = 6607;
       response = userSteps.updateUser(userId,name,email,gender,status).statusCode(200).log().body().body("name", equalTo(name), "email", equalTo(email));
    }

    @And("^I verify if the new user details are updated in the records$")
    public void iVerifyIfTheNewUserDetailsAreUpdatedInTheRecords() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByFirstName(name);
        Assert.assertThat(userMap, hasValue(name));
        userId = (int) userMap.get("id");
        System.out.println(userId);
    }

    @When("^I send a DELETE request to the application$")
    public void iSendADELETERequestToTheApplication() {
        response = userSteps.deleteUser(userId).statusCode(204).log().status();
    }

    @And("^I verify if the new user record is deleted from the application$")
    public void iVerifyIfTheNewUserRecordIsDeletedFromTheApplication() {
        response = userSteps.getUserById(userId).statusCode(404).log().status();
    }
}
