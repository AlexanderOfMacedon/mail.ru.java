package mail.ru;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreeSerializerTest {

    class Person {
        private final String firstName;
        private final String lastName;
        private final Address address;
        private final List<String> phoneNumbers;
        private final Map<String, Integer> rating;

        Person(String firstName, String lastName, Address address, List<String> phoneNumbers, Map<String, Integer> rating) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phoneNumbers = phoneNumbers;
            this.rating = rating;
        }
    }

    class Address {
        private final String city;
        private final String postalCode;

        Address(String city, String postalCode) {
            this.city = city;
            this.postalCode = postalCode;
        }
    }

    Address testAddress = new Address("Lipetsk", "100500");

    public ArrayList<String> phonesForTest() {
        ArrayList<String> phonesList = new ArrayList<>();
        phonesList.add("123124124");
        phonesList.add("424-346345-1");
        return phonesList;
    }

    public Map<String, Integer> ratingForTest() {
        HashMap<String, Integer> rating = new HashMap<>();
        rating.put("math", 5);
        rating.put("english", 3);
        return rating;
    }

    Person testPerson = new Person("Darya", "Kryazheva", testAddress, phonesForTest(), ratingForTest());

    @Test
    public void serializeXml() throws IllegalAccessException {
        String expectedValue = "<Person>\n" +
                "   <firstName>Darya</firstName>\n" +
                "   <lastName>Kryazheva</lastName>\n" +
                "   <address>\n" +
                "      <city>Lipetsk</city>\n" +
                "      <postalCode>100500</postalCode>\n" +
                "   </address>\n" +
                "   <phoneNumbers>\n" +
                "         <1>123124124</1>\n" +
                "         <2>424-346345-1</2>\n" +
                "   </phoneNumbers>\n" +
                "   <rating>\n" +
                "         <english>3</english>\n" +
                "         <math>5</math>\n" +
                "   </rating>\n" +
                "</Person>\n";
        TreeSerializer xmlSerializer = new TreeSerializer(new OutputXml());
        Assert.assertEquals(xmlSerializer.serialize(testPerson), expectedValue);
    }

    @Test
    public void serializeJson() throws IllegalAccessException {
        String expectedValue = "\"Person\": {\n" +
                "   \"firstName\": Darya,\n" +
                "   \"lastName\": Kryazheva,\n" +
                "   \"address\": {\n" +
                "      \"city\": Lipetsk,\n" +
                "      \"postalCode\": 100500,\n" +
                "   },\n" +
                "   \"phoneNumbers\": {\n" +
                "         123124124,\n" +
                "         424-346345-1,\n" +
                "   },\n" +
                "   \"rating\": {\n" +
                "         \"english\": 3,\n" +
                "         \"math\": 5,\n" +
                "   },\n" +
                "},\n";
        TreeSerializer jsonSerializer = new TreeSerializer(new OutputJson());
        Assert.assertEquals(jsonSerializer.serialize(testPerson), expectedValue);
    }
}