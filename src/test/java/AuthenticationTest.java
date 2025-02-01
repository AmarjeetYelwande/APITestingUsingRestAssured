import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class AuthenticationTest extends BaseTest {
    protected RequestSpecification spec;

    @BeforeMethod
    void Setup(){
        spec = new RequestSpecBuilder().setBaseUri("https://dummyjson.com/auth").build();
    }

    @Test
    void bearerAuthentication(){
        JSONObject body = new JSONObject();
        body.put("username", "emilys");
        body.put("password", "emilyspass");
        body.put("expiresInMins", 200);
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/login");

        String authToken = response.jsonPath().getString("accessToken");

        Response responseSuccessfulAuth = given(spec)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + authToken)
                .body(body.toString())
                .get("/me");

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it's not");
        Assert.assertEquals(responseSuccessfulAuth.getStatusCode(), 200, "Status code should be 200, but it's not");
        String firstName = responseSuccessfulAuth.jsonPath().getString("firstName");
        String lastName = responseSuccessfulAuth.jsonPath().getString("lastName");
        String email = responseSuccessfulAuth.jsonPath().getString("email");
        Assert.assertEquals(firstName, "Emily");
        Assert.assertEquals(lastName, "Johnson");
        Assert.assertEquals(email, "emily.johnson@x.dummyjson.com");

        JSONObject expectedResponse = getJsonObject();
        JSONObject actualResponse = new JSONObject(responseSuccessfulAuth.print());
        Assert.assertTrue(expectedResponse.similar(actualResponse));
    }

    private static JSONObject getJsonObject() {
        String jsonString = "{\"id\":1,\"firstName\":\"Emily\",\"lastName\":\"Johnson\",\"maidenName\":\"Smith\",\"age\":28,\"gender\":\"female\",\"email\":\"emily.johnson@x.dummyjson.com\",\"phone\":\"+81 965-431-3024\",\"username\":\"emilys\",\"password\":\"emilyspass\",\"birthDate\":\"1996-5-30\",\"image\":\"https://dummyjson.com/icon/emilys/128\",\"bloodGroup\":\"O-\",\"height\":193.24,\"weight\":63.16,\"eyeColor\":\"Green\",\"hair\":{\"color\":\"Brown\",\"type\":\"Curly\"},\"ip\":\"42.48.100.32\",\"address\":{\"address\":\"626 Main Street\",\"city\":\"Phoenix\",\"state\":\"Mississippi\",\"stateCode\":\"MS\",\"postalCode\":\"29112\",\"coordinates\":{\"lat\":-77.16213,\"lng\":-92.084824},\"country\":\"United States\"},\"macAddress\":\"47:fa:41:18:ec:eb\",\"university\":\"University of Wisconsin--Madison\",\"bank\":{\"cardExpire\":\"03/26\",\"cardNumber\":\"9289760655481815\",\"cardType\":\"Elo\",\"currency\":\"CNY\",\"iban\":\"YPUXISOBI7TTHPK2BR3HAIXL\"},\"company\":{\"department\":\"Engineering\",\"name\":\"Dooley, Kozey and Cronin\",\"title\":\"Sales Manager\",\"address\":{\"address\":\"263 Tenth Street\",\"city\":\"San Francisco\",\"state\":\"Wisconsin\",\"stateCode\":\"WI\",\"postalCode\":\"37657\",\"coordinates\":{\"lat\":71.814525,\"lng\":-161.150263},\"country\":\"United States\"}},\"ein\":\"977-175\",\"ssn\":\"900-590-289\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36\",\"crypto\":{\"coin\":\"Bitcoin\",\"wallet\":\"0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a\",\"network\":\"Ethereum (ERC20)\"},\"role\":\"admin\"}";
        return new JSONObject(
                jsonString
        );
    }
}
