
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.collections4.IteratorUtils.forEach;

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

    @DataProvider(name = "DataForPostFromCSV")
    public Bookings[] createDataUsingCSV() throws IOException {
        String fileName = "src/test/Data/BookingData.csv";
        List<Bookings> postData = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Bookings.class)
                .build()
                .parse();
        System.out.println(postData.toString());
        ArrayList<Bookings> a = new ArrayList<>();
        var test = postData.toArray(new Bookings[0]);
        System.out.println(Arrays.toString(test));
        return test;

    }

}
