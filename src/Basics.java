import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.commonMethods;
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
		.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString(); // extract the response as string
		
		// Add place --> Update Place with new address --> Get place to validate if the New Address is present in the response  
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response); // for parsing Json - parse data to variable (context outline)
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		// update - PUT 
		String newAddress = "Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{ \r\n"
						+ "\r\n"
						+ "\"place_id\":\""+placeId+"\", \r\n"
						+ "\r\n"
						+ "\"address\":\""+newAddress+"\", \r\n"
						+ "\r\n"
						+ "\"key\":\"qaclick123\" \r\n"
						+ "\r\n"
						+ "} ")
				.when().put("maps/api/place/update/json")
				.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		// make a get to validate the previous put and check if the updated address is correct
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString(); 
		
		JsonPath js1 = commonMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
		// compare the given address with the actual
		
		
		
	}

}
