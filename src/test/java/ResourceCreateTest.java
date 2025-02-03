import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ResourceCreateTest extends BaseTest {

    @Test
    public void createBookingRecord() {
        Response response = createRecord();
        response.print();
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201, but it's not");

        SoftAssert softAssert = new SoftAssert();
        String actualFirstName = response.jsonPath().getString("bookings.firstname");
        softAssert.assertEquals(actualFirstName, "Steve", "firstname in response is not expected");

        String actualLastName = response.jsonPath().getString("bookings.lastname");
        softAssert.assertEquals(actualLastName, "Smith", "lastname in response is not expected");

        int price = response.jsonPath().getInt("bookings.totalprice");
        softAssert.assertEquals(price, 150, "totalprice in response is not expected");

        boolean depositpaid = response.jsonPath().getBoolean("bookings.depositpaid");
        softAssert.assertFalse(depositpaid, "depositpaid should be false, but it's not");

        String actualCheckin = response.jsonPath().getString("bookings.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "2020-03-25", "checkin in response is not expected");

        String actualCheckout = response.jsonPath().getString("bookings.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2020-03-27", "checkout in response is not expected");

        String actualAdditionalneeds = response.jsonPath().getString("bookings.additionalneeds");
        softAssert.assertEquals(actualAdditionalneeds, "Full Breakfast", "additionalneeds in response is not expected");

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

    @Test
    public void uploadCSV() {
            String response = RestAssured.given().multiPart("file2", new File("src/test/Data/BookingData.csv")).
                    when().post("http://localhost:3000/bookings").then().extract().asString();
            System.out.println("Response is : " + response);
        }

    @Test
    public void saveToCSV() throws IOException {
        JsonNode jsonTree;
        String responseAsString = given(spec)
                .get("/bookings")
                .asString();
        FileWriter JsonFile = new FileWriter("src/test/Data/responseSavedInJson.json");
        JsonFile.write(responseAsString);
        JsonFile.flush();
        JsonFile.close();

        jsonTree = new ObjectMapper().readTree(new File("src/test/Data/sample.json"));

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File("src/test/Data/sampleJsonToCSV.csv"), jsonTree);
    }
}
