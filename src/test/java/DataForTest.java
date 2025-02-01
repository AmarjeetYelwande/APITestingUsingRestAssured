
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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
                1, 2
        };
    }

    @DataProvider(name = "DataForPost")
    public Object[][] createData() {
        return new Object[][] {
                {"Albert", "Einstein", 150, false, "2020-03-25", "2020-03-27", "Baby crib"},
                {"Thomas", "Edison",  150, false, "2020-03-25", "2020-03-27", "full"},
                {"Henry", "Ford",  150, false, "2020-03-25", "2020-03-27", "half"}
        };
    }
}
