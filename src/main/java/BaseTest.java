import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.json.JSONObject;

public class BaseTest {
    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().
                setBaseUri("http://localhost:3000").
                build();
    }
    protected Response createRecord() {
        // Create JSON body
        JSONObject body = new JSONObject();
        body.put("firstname", "Dmitry");
        body.put("lastname", "Shyshkin");
        body.put("totalprice", 150);
        body.put("depositpaid", false);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", "2020-03-25");
        bookingdates.put("checkout", "2020-03-27");
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", "Baby crib");

        JSONObject mainObj = new JSONObject();
        mainObj.put("bookings", body);

        // Get response
        return RestAssured.given(spec).contentType(ContentType.JSON).body(mainObj.toString())
                .post("/bookings");
    }
}
