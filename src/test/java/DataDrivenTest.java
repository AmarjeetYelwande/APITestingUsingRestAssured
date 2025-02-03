import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import java.util.List;

public class DataDrivenTest extends DataForTest {

    @Test(dataProvider = "DataForPost")
    public void simplePostRequest(String firstname,
                                  String lastname,
                                  Integer totalprice,
                                  Boolean despositpaid,
                                  String checkin,
                                  String checout,
                                  String additionalneeds
                                  ) {

        JSONObject body = new JSONObject();
        body.put("firstname", firstname);
        body.put("lastname", lastname);
        body.put("totalprice", totalprice);
        body.put("depositpaid", despositpaid);

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", checkin);
        bookingdates.put("checkout",  checout);
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", additionalneeds);

        JSONObject mainObj = new JSONObject();
        mainObj.put("bookings", body);

        given(spec).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Content-Type", "application/json").
                body(mainObj.toString()).
                when().
                post("/bookings").then().statusCode(201);
    }

    // Commenting for now as it needs work to be done
    @Test(dataProvider = "DataForPostFromCSV", enabled = false)
    public void simplePostRequestUsingCSV(String firstname,
                                  String lastname,
                                  Integer totalprice,
                                  String despositpaid,
                                  String checkin,
                                  String checout,
                                  String additionalneeds
    ) {

        JSONObject body = new JSONObject();
        body.put("firstname", firstname);
        body.put("lastname", lastname);
        body.put("totalprice", totalprice);
        body.put("depositpaid", Boolean.parseBoolean(despositpaid));

        JSONObject bookingdates = new JSONObject();
        bookingdates.put("checkin", checkin);
        bookingdates.put("checkout",  checout);
        body.put("bookingdates", bookingdates);
        body.put("additionalneeds", additionalneeds);

        JSONObject mainObj = new JSONObject();
        mainObj.put("bookings", body);

        given(spec).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Content-Type", "application/json").
                body(mainObj.toString()).
                when().
                post("/bookings").then().statusCode(201);
    }


    @Test(dataProvider = "dataForDelete")
    public void SimpleDeleteRequest(int Id) {
        Response responseDelete = RestAssured.given(spec).when()
                .delete("/bookings/" + Id);
        System.out.println(responseDelete.statusCode());
        responseDelete.print();
    }

    // Disabling this test as it needs right-clicking testng.xml to run test
    @Parameters({"id"})
    @Test(enabled = false)
    public void readUserIDFromTestNGXMLDeleteTheUser(int id) {
        System.out.println("UserId is : " + id);
        RestAssured.given(spec).when().delete("/bookings/" + id).then().log().all();
    }

    @Test
    public void DeleteOneBooking() {
        spec.pathParam("id", 6);
        given(spec).when().delete("/bookings/{id}").then().statusCode(200);
        // Make sure that this resource no longer exists
        given(spec).when().get("/bookings/{id}").then().statusCode(404);
    }

    @Test
    public void deleteAllBookings() {

        GetTest ids = new GetTest();
        ids.getAllIds();
        List<Integer> allIdsToDelete = ids.allIds;
        for (int id : allIdsToDelete) {
            System.out.println("Current booking ID to delete is " + id);
            spec.pathParam("id", id);
            given(spec).when().delete("/bookings/{id}").then().statusCode(200);
            // Make sure that this resource no longer exists
            given(spec).when().get("/bookings/{id}").then().statusCode(404);
        }
    }
}