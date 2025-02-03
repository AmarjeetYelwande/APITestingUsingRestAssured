import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class GetTest extends BaseTest {

    List<Integer> allIds;

    @Test(groups = { "group1", "group2" })
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

    @Test
    public void validateSchema(){
        RestAssured.given(spec).when().get("/Bookings/1").then().assertThat()
                    .body(matchesJsonSchema(new File("src/test/Data/Schema.json")));
        }
}