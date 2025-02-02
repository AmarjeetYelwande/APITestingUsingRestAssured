import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetTest extends BaseTest {

    List<Integer> allIds;

    @Test
    public void simpleGetRequest() {
        RestAssured.given(spec).when().get("/").then().statusCode(200).log().all();
    }
    @Test
    public void getAllIds() {
        Response getAllIds = given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Content-Type", "application/json").
                when().
                get("http://localhost:3000/bookings");
        allIds = getAllIds.jsonPath().getList("id");
        System.out.println("List of booking ID's : " + allIds);
        Assert.assertEquals( getAllIds.statusCode(), 200);
    }
}
