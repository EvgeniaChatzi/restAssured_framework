import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		
		// given - all input details
		// when - submit the API - resource, HTTP method
		// then - validate the response
		
		// add place POST
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); // for parsing Json - parse data to variable
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		// update - PUT 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{ \r\n"
						+ "\r\n"
						+ "\"place_id\":\""+placeId+"\", \r\n"
						+ "\r\n"
						+ "\"address\":\"70 winter walk, USA\", \r\n"
						+ "\r\n"
						+ "\"key\":\"qaclick123\" \r\n"
						+ "\r\n"
						+ "} ")
				.when().put("maps/api/place/update/json")
				.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		// make a get to validate the previous put and check if the updated address is correct
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
	}

}
