package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

public class demoFeature {
    @Given("Setting up RestAssured")
    public void setting_up_rest_assured() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @When("Deserialize JSON to ArrayList")
    public void deserialize_json_to_array_list() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("users?page=2");

        JsonPath jsonPath = response.jsonPath();
        List<dataNode> dataNodes = jsonPath.getList("data", dataNode.class);
        for (dataNode node: dataNodes) {
            System.out.println(node.getFirst_name());
        }
    }

    @Then("Do nothing")
    public void do_nothing() {
        System.out.println("...");
    }
}