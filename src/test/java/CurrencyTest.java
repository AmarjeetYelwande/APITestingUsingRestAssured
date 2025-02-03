import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;

public class CurrencyTest {

    // Free exchange rate API documentation is available at below place
    // https://frankfurter.dev/
    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().
                setBaseUri("https://api.frankfurter.dev/v1").
                setContentType(ContentType.JSON).
                build();
    }

    @Test
    void getCurrencyPairDiff() {
        var response = RestAssured.given(spec)
                .when().get("/2022-01-01..2022-01-31?base=USD&symbols=GBP");
        ArrayList<Float> allCurrencyPairs = JsonPath.read(response.print(), "$.rates[*].GBP");
        System.out.println(allCurrencyPairs.toString());
        System.out.println("Maximum currency pair rate for USD and GBP during january 2022 was : " + Collections.max(allCurrencyPairs));
    }

    @Test
    void getSupportedCurrencyNames() {
        var response = RestAssured.given(spec)
                .when().get("/currencies");
        System.out.println(response.print());
        // Get description of BGN currency
        System.out.println("description of BGN currency is : " + response.jsonPath().get("BGN"));
    }

    @Test
    void getMaximumCurrencyRateTodayAgainstUSD() {
        var response = RestAssured.given(spec)
                .when().get("/latest?base=USD");
        System.out.println(response.print());
        ArrayList<Double> allCurrenciesToday = JsonPath.read(response.print(), "$.rates.[*]");
        System.out.println("Type of element in the array is : " + allCurrenciesToday.get(0).getClass().getName());
        System.out.println(allCurrenciesToday);
        System.out.println("Maximum currency rate against USD is : " + (Collections.max(allCurrenciesToday)));
    }
}