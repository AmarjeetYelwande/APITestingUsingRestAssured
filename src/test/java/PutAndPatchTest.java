import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;
import static io.restassured.RestAssured.given;

public class PutAndPatchTest extends BaseTest {

    @Test
    public void putRecord() {
        GetTest ids = new GetTest();
        ids.getAllIds();
        List<Integer> allIdsToDelete = ids.allIds;
        Response putResponse = given(spec).when().body(new File("src/test/Data/Put.json")).put("/bookings/" + allIdsToDelete.get(0));
        putResponse.print();
    }

    @Test
    public void patchRecord() {
        GetTest ids = new GetTest();
        ids.getAllIds();
        List<Integer> allIdsToDelete = ids.allIds;
        Response putResponse = given(spec).when().body(new File("src/test/Data/Patch.json")).patch("/bookings/" + allIdsToDelete.get(0));
        Assert.assertEquals(putResponse.getStatusCode() , 200);
    }
}
