
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
public class DataForTest {
    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().
                setBaseUri("http://localhost:3000").
                build();
    }

    @DataProvider(name = "dataForDelete")
    public Object[] dataForDelete() {
        return new Object[]{
                1,2
        };
    }

    @DataProvider(name = "DataForPost")
    public JSONObject createData() {
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

        return body;
    }
}
