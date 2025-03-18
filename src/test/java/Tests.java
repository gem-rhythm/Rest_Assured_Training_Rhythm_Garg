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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

public class Tests {
    @BeforeTest
    public void setting_up_rest_assured() {
        RestAssured.baseURI = "https://reqres.in/api/";
    }

    @Test
    public void validateStatusCode() {
//        RequestSpecification httpRequest = RestAssured.given();
//        Response response = httpRequest.get("users?page=1");
//        int statusCode = response.statusCode();
//
//        Assert.assertEquals(statusCode, 200, "Correct Status Code...");
    }

    @Test
    public void validateResponseHeader() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("");
        Headers allHeaders = response.headers();

        Assert.assertEquals(allHeaders.get("Content-Type").getValue(), "text/html; charset=utf-8", "Correct content type received...");
    }

    @Test
    public void readJSONResponse() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("users?page=1");
        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void createUser() {
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Rhythm");
        requestParams.put("job", "TBD");

        httpRequest.header("Content-Type", "application/json");
        httpRequest.body(requestParams.toString());

        Response response = httpRequest.post("api/users");
        System.out.println(response.getBody().asPrettyString());
    }


    @Test
    public void serializeObject() {
        customObject obj = new customObject(10, 20);
        serializeObject(obj, "serialized_customObject");
    }

    public void serializeObject(Object obj, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deserializeObject() {
        customObject deserializedObject = deserializeObject("serialized_customObject");
        System.out.println(deserializedObject.a);
        System.out.println(deserializedObject.b);
    }

    public customObject deserializeObject(String fileName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object deserializedObject = objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            return (customObject) deserializedObject;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void validateLogin() {
        RequestSpecification httpRequest = RestAssured.given().auth().preemptive().basic(
                "rhygarg",
                "rhypass"
        );

        Response res = httpRequest.get("https://postman-echo.com/basic-auth");
        ResponseBody body = res.body();
        System.out.println("Response Body: " + body.asPrettyString());
    }

    @Test
    public void sendPUTReq() {
        RequestSpecification httpRequest = RestAssured.given();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "invincible");
        jsonObject.put("job", "saving the world.");

        Response response = httpRequest.put("users/2");
        System.out.println("Response of PUT Req: " + response.body().asPrettyString());
        Assert.assertEquals(response.statusCode(), 200, "Result not as expected...");
    }

    @Test
    public void deserialize_json_to_array_list() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("users?page=2");

        JsonPath jsonPath = response.jsonPath();
        List<dataNode> dataNodes = jsonPath.getList("data", dataNode.class);
        for (dataNode node: dataNodes) {
            System.out.println(node.getFirst_name());
        }
    }
}
