import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class DataDrivenTests extends DataForTest {

    @Test
    public void simpleGetRequest(){
        RestAssured.given(spec).when().get("/").then().statusCode(200).log().all();
    }

    @Test(dataProvider = "DataForPost")
    public void simplePostRequest(JSONObject request) {
        given(spec).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Content-Type", "application/json").
                body(request.toString()).
                when().
                post("/bookings").
                then().
                statusCode(201).
                log().all();
    }

    @Test(dataProvider = "dataForDelete")
    public void SimpleDeleteRequest(int Id) {
        Response responseDelete = RestAssured.given(spec).when()
                .delete("/bookings/" + Id);
        System.out.println(responseDelete.statusCode());
        responseDelete.print();
    }

    @Parameters({"id"})
    @Test
    public void readUserIDFromTestNGXMLDeleteTheUser(int id) {
        System.out.println("UserId is : {$id}" + id);
        RestAssured.given(spec).when().delete("/bookings/" + id).then().log().all();
    }
}
