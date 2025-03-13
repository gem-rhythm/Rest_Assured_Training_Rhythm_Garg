import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DemoTest {
    @Test
    public void getPetsByStatus() {
        given()
            .header("accept", "application/json")
        .when()
            .get("https://petstore3.swagger.io/api/v3/pet/findByStatus?status=available")
        .then()
            .assertThat().statusCode(200);
    }
}
