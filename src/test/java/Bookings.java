import com.opencsv.bean.CsvBindByName;

public class Bookings {
    @CsvBindByName(column = "firstname")
    private String firstname;

    @CsvBindByName(column = "lastname")
    private String lastname;

    @CsvBindByName(column = "totalprice")
    private Integer totalprice;

    @CsvBindByName(column = "depositpaid")
    private String depositpaid;

    @CsvBindByName(column = "checkin")
    private String checkin;

    @CsvBindByName(column = "checkout")
    private String checkout;

    @CsvBindByName(column = "additionalneeds")
    private String additionalneeds;
}