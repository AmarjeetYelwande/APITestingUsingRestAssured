import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class ResourceCreateTest extends BaseTest{

    @Test
    public void createBookingRecord() {
        Response response = createRecord();
        response.print();
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201, but it's not");

        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("bookings.firstname");
        softAssert.assertEquals(actualFirstName, "Dmitry", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("bookings.lastname");
        softAssert.assertEquals(actualLastName, "Shyshkin", "lastname in response is not expected");

        int price = response.jsonPath().getInt("bookings.totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("bookings.depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("bookings.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookings.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("bookings.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Baby crib", "additionalneeds in response is not expected");

        softAssert.assertAll();
    }

    @Test
    public void healthCheckTest() {
        given().
                spec(spec).
                when().
                get().
                then().
                assertThat().
                statusCode(200);
    }
}
