import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthenticationTests extends BaseTest {

    protected RequestSpecification spec;

    @BeforeMethod
    void Setup(){
        spec = new RequestSpecBuilder().setBaseUri("https://dummyjson.com/auth").build();
    }

    @Test
    void basicAuthentication(){

        JSONObject body = new JSONObject();
        body.put("username", "emilys");
        body.put("password", "emilyspass");
        body.put("expiresInMins", 200);

        Response response = RestAssured.given(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/login");
        response.print();

        String authToken = response.jsonPath().getString("accessToken");

        Response responseSuccessfulAuth = RestAssured.given(spec)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(body.toString())
                .get("/me");
        responseSuccessfulAuth.print();
    }
}
